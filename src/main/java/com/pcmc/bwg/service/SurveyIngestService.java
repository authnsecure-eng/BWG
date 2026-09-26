package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.ingest.SurveyIngestRequest;
import com.pcmc.bwg.dto.ingest.WasteVisitIngestDto;
import com.pcmc.bwg.entity.BwgSurvey;
import com.pcmc.bwg.entity.BwgSurveyWasteVisit;
import com.pcmc.bwg.entity.Zone;
import com.pcmc.bwg.entity.enums.SurveyStatus;
import com.pcmc.bwg.repository.BwgSurveyRepository;
import com.pcmc.bwg.repository.BwgSurveyWasteVisitRepository;
import com.pcmc.bwg.repository.ZoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Maps a survey submitted through the mobile app (SurveyIngestRequest, whose
 * field names mirror the mobile app's own SurveyCreateRequest) onto a
 * BwgSurvey row, so it shows up in the Admin "Reports" screen the same way a
 * manually-seeded row does.
 *
 * Upsert key: mobileSurveyId. The mobile app calls this every time a survey
 * is created or updated there (waste-visit day added, CPCB flag changed), so
 * this always finds-or-creates by that id rather than assuming "create
 * happens once".
 *
 * Field-mapping notes (see the field-mapping table shared separately for the
 * full list):
 *  - Many mobile-only concepts (biogas plant, geofencing, surveyor
 *    self-verification, CPCB filing, water bill detail) did not exist in
 *    bwg_survey before this bridge and were added as new nullable columns
 *    in V9__add_mobile_survey_bridge_fields.sql - see BwgSurvey's "bridge
 *    fields" section.
 *  - zone: resolved by name against the Zone master; if no match is found
 *    (e.g. spelling differs) the raw text is kept in zoneNameSnapshot so the
 *    submission is never silently dropped, but zoneId stays null until an
 *    admin reconciles it - same pattern already used by
 *    V5__add_bwg_survey_zone_id.sql for the old free-text "zone" column.
 *  - hasBiogasPlant is also copied onto onsiteProcessingAvailable, since a
 *    biogas plant on-site is a form of onsite processing in this domain.
 *    Revisit if that assumption turns out to be wrong.
 *  - Day-wise waste visits are upserted by day number into
 *    bwg_survey_waste_visit, and the *_kg_day aggregate columns are also
 *    refreshed as a simple average across all visits so the existing
 *    Reports list/detail view (which reads those columns) shows real
 *    numbers instead of staying null.
 */
@Service
public class SurveyIngestService {

    private static final Logger log = LoggerFactory.getLogger(SurveyIngestService.class);

    private final BwgSurveyRepository bwgSurveyRepository;
    private final BwgSurveyWasteVisitRepository wasteVisitRepository;
    private final ZoneRepository zoneRepository;

    public SurveyIngestService(BwgSurveyRepository bwgSurveyRepository,
                                BwgSurveyWasteVisitRepository wasteVisitRepository,
                                ZoneRepository zoneRepository) {
        this.bwgSurveyRepository = bwgSurveyRepository;
        this.wasteVisitRepository = wasteVisitRepository;
        this.zoneRepository = zoneRepository;
    }

    @Transactional
    public BwgSurvey ingest(SurveyIngestRequest req) {
        Optional<BwgSurvey> existingOpt = bwgSurveyRepository.findByMobileSurveyId(req.getMobileSurveyId());
        BwgSurvey survey = existingOpt.orElseGet(BwgSurvey::new);
        boolean isNew = existingOpt.isEmpty();

        if (isNew) {
            survey.setMobileSurveyId(req.getMobileSurveyId());
            survey.setApplicationNo(generateApplicationNo());
            survey.setStatus(SurveyStatus.SUBMITTED);
            survey.setSubmittedAt(OffsetDateTime.now());
            survey.setCreatedAt(OffsetDateTime.now());
        }
        // Status is intentionally left untouched on updates: if an admin has
        // already moved this survey to COMPLETED/REJECTED, a later mobile-side
        // edit (e.g. another day's waste visit) must not silently flip it back.

        survey.setCategory(capitalize(req.getCategory()));
        survey.setEstablishmentName(req.getEstablishmentName());
        survey.setContactPersonName(req.getContactName());
        survey.setDesignation(req.getContactDesignation());
        survey.setMobileNo(req.getContactMobile());
        survey.setEmail(req.getContactEmail());
        survey.setFullAddress(req.getContactAddress());
        survey.setPinCode(req.getContactPincode());
        survey.setYearEstablished(parseInt(req.getYearEstablished()));
        survey.setBuiltUpAreaSqM(req.getBuiltUpAreaSqM());
        survey.setPremisesPhotoPath(req.getPremisesPhotoUrl());
        survey.setPremisesPhotoGeo(req.getPremisesPhotoGeo());
        survey.setPremisesPhotoTime(req.getPremisesPhotoTime());
        survey.setSignagePhotoPath(req.getSignagePhotoUrl());
        survey.setSignagePhotoGeo(req.getSignagePhotoGeo());
        survey.setSignagePhotoTime(req.getSignagePhotoTime());
        survey.setSubCategoryType(req.getSubCategoryType());
        survey.setSocietyName(req.getSocietyName());
        survey.setChsRegNo(req.getChsRegNo());
        survey.setOrganizationName(req.getOrgName());
        survey.setRegistrationCinNo(req.getCinNumber());
        survey.setTradeLicenseNo(req.getTradeLicenseNo());
        survey.setPtin(req.getPtin());
        survey.setGstin(req.getGstin());
        survey.setMobileTotalFloors(req.getTotalFloors());
        survey.setNoOfFloors(parseInt(req.getTotalFloors()));
        survey.setMobileTotalUnits(req.getTotalUnits());
        survey.setBuildingRemarks(req.getBuildingRemarks());
        survey.setBuildingPermissionRefNo(req.getBuildingPermissionRefNo());
        survey.setBuildingPermissionDocPath(req.getBuildingPermissionDocUrl());
        survey.setBuildingPermissionGeo(req.getBuildingPermissionGeo());
        survey.setBuildingPermissionTime(req.getBuildingPermissionTime());
        survey.setWaterConsumerNo(req.getWaterConsumerNo());
        survey.setWaterConsumptionLpd(req.getDailyWaterConsumptionLiters());
        survey.setWaterBillingPeriod(req.getWaterBillingPeriod());
        survey.setWaterUnitsConsumed(req.getWaterUnitsConsumed());
        survey.setWaterBillDocPath(req.getWaterBillDocUrl());
        survey.setBinInfrastructure(req.getBinInfrastructure());
        survey.setWasteSegregatedAtSource(req.getSegregatedAtSource());
        survey.setDryWasteChannelizedTo(req.getDryWasteChannelizedTo());
        survey.setOverallDisposalMode(req.getOverallDisposalMode());
        survey.setVendorName(req.getVendorName());
        survey.setMouValidity(req.getMouValidity());
        survey.setProcessingDestination(req.getProcessingDestination());
        survey.setPrivateVendorDetails(req.getPrivateVendorDetails());
        survey.setHasBiogasPlant(req.getHasBiogasPlant() != null ? req.getHasBiogasPlant() : Boolean.FALSE);
        survey.setOnsiteProcessingAvailable(req.getHasBiogasPlant());
        survey.setProcessingMethod(req.getProcessingMethod());
        survey.setBiogasCapacity(req.getBiogasCapacity());
        survey.setBiogasCapacityUnit(req.getBiogasCapacityUnit());
        survey.setBiogasSpaceAvailableSqM(req.getSpaceAvailableSqMeters());
        survey.setBiogasByProductUsage(req.getByProductUsage());
        survey.setBiogasOperationalStatus(req.getBiogasOperationalStatus());
        survey.setBiogasPhotoPath(req.getBiogasPhotoUrl());
        survey.setBiogasRemarks(req.getBiogasRemarks());
        survey.setWasteGivenToOtherAgency(req.getWasteGivenToOtherAgency());
        survey.setAgencyDocumentPhotoPath(req.getAgencyDocumentPhotoUrl());
        survey.setGeofenceLatitude(req.getGeofenceLatitude());
        survey.setGeofenceLongitude(req.getGeofenceLongitude());
        survey.setGeofenceRadiusMeters(req.getGeofenceRadiusMeters());
        survey.setGeofencePhotoPath(req.getGeofencePhotoUrl());
        survey.setEligibilityFloorArea(req.getEligibilityFloorArea());
        survey.setEligibilityWaterConsumption(req.getEligibilityWaterConsumption());
        survey.setEligibilitySolidWaste(req.getEligibilitySolidWaste());
        survey.setDeclarantName(req.getDeclarantName());
        survey.setDeclarantDesignation(req.getDeclarantDesignation());
        survey.setDeclarantOrganization(req.getDeclarantOrgName());
        survey.setDeclarationDate(parseDate(req.getDeclarantDate()));
        survey.setDeclarationPlace(req.getDeclarantPlace());
        survey.setDeclarationFormPath(req.getDeclarationFileUrl());
        survey.setDeclarationFileGeo(req.getDeclarationFileGeo());
        survey.setDeclarationFileTime(req.getDeclarationFileTime());
        survey.setSurveyorName(req.getSurveyorName());
        survey.setSurveyorSelfiePath(req.getSurveyorSelfieUrl());
        survey.setSurveyorGps(req.getSurveyorGps());
        survey.setSurveyorTimestamp(req.getSurveyorTimestamp());
        survey.setSurveyorVerified(req.getSurveyorVerified() != null ? req.getSurveyorVerified() : Boolean.FALSE);
        survey.setCpcbCompleted(req.getCpcbCompleted() != null ? req.getCpcbCompleted() : Boolean.FALSE);
        survey.setCpcbAckNumber(req.getCpcbAckNumber());
        survey.setCpcbSubmissionDate(req.getCpcbSubmissionDate());
        survey.setElectoralWardName(req.getElectoralWard());
        survey.setWard(req.getWard());
        survey.setSurveyOfficerId(req.getSurveyOfficerId());
        survey.setSurveyOfficerName(req.getSurveyOfficerName());

        applyZone(survey, req.getZone());
        applyGpsCoordinates(survey, req.getGpsCoordinates());

        survey.setUpdatedAt(OffsetDateTime.now());

        survey = bwgSurveyRepository.save(survey);
        applyWasteVisits(survey, req.getWasteVisits());

        log.info("Ingested survey from mobile app: mobileSurveyId={}, bwgSurveyId={}, applicationNo={}, isNew={}",
                req.getMobileSurveyId(), survey.getId(), survey.getApplicationNo(), isNew);

        return survey;
    }

    private void applyZone(BwgSurvey survey, String zoneName) {
        if (zoneName == null || zoneName.isBlank()) {
            return;
        }
        Optional<Zone> zoneOpt = zoneRepository.findByNameIgnoreCase(zoneName.trim());
        if (zoneOpt.isPresent()) {
            survey.setZoneId(zoneOpt.get().getId());
            survey.setZoneNameSnapshot(zoneOpt.get().getName());
        } else {
            log.warn("No Zone master match for mobile survey zone '{}' (mobileSurveyId={}); "
                    + "keeping the raw name in zoneNameSnapshot until an admin reconciles it.",
                    zoneName, survey.getMobileSurveyId());
            survey.setZoneNameSnapshot(zoneName.trim());
        }
    }

    private void applyGpsCoordinates(BwgSurvey survey, String gpsCoordinates) {
        if (gpsCoordinates == null || !gpsCoordinates.contains(",")) {
            return;
        }
        String[] parts = gpsCoordinates.split(",", 2);
        try {
            survey.setLatitude(new BigDecimal(parts[0].trim()));
            survey.setLongitude(new BigDecimal(parts[1].trim()));
        } catch (NumberFormatException ex) {
            log.warn("Could not parse gpsCoordinates '{}' for mobileSurveyId={}", gpsCoordinates, survey.getMobileSurveyId());
        }
    }

    private void applyWasteVisits(BwgSurvey survey, List<WasteVisitIngestDto> visits) {
        if (visits == null || visits.isEmpty()) {
            return;
        }

        BigDecimal wetSum = BigDecimal.ZERO;
        BigDecimal drySum = BigDecimal.ZERO;
        BigDecimal gardenSum = BigDecimal.ZERO;
        BigDecimal totalSum = BigDecimal.ZERO;
        int count = 0;

        for (WasteVisitIngestDto dto : visits) {
            if (dto.getDayNumber() == null) {
                continue;
            }
            BigDecimal wet = dto.getWetWasteKg() != null ? dto.getWetWasteKg() : BigDecimal.ZERO;
            BigDecimal dry = dto.getDryWasteKg() != null ? dto.getDryWasteKg() : BigDecimal.ZERO;
            BigDecimal garden = dto.getGardenWasteKg() != null ? dto.getGardenWasteKg() : BigDecimal.ZERO;
            BigDecimal total = dto.getTotalWasteKg() != null ? dto.getTotalWasteKg() : wet.add(dry).add(garden);

            BwgSurveyWasteVisit visit = wasteVisitRepository
                    .findByBwgSurvey_IdAndDayNumber(survey.getId(), dto.getDayNumber())
                    .orElseGet(BwgSurveyWasteVisit::new);
            visit.setBwgSurvey(survey);
            visit.setDayNumber(dto.getDayNumber());
            visit.setVisitDate(dto.getVisitDate());
            visit.setWetWasteKg(wet);
            visit.setDryWasteKg(dry);
            visit.setGardenWasteKg(garden);
            visit.setTotalWasteKg(total);
            visit.setWetWastePhotoPath(dto.getWetWastePhotoUrl());
            visit.setDryWastePhotoPath(dto.getDryWastePhotoUrl());
            visit.setGardenWastePhotoPath(dto.getGardenWastePhotoUrl());
            visit.setWeighingScalePhotoPath(dto.getWeighingScalePhotoUrl());
            visit.setHandoverAreaPhotoPath(dto.getHandoverAreaPhotoUrl());
            visit.setIsCompleted(dto.getIsCompleted() != null ? dto.getIsCompleted() : Boolean.FALSE);
            wasteVisitRepository.save(visit);

            wetSum = wetSum.add(wet);
            drySum = drySum.add(dry);
            gardenSum = gardenSum.add(garden);
            totalSum = totalSum.add(total);
            count++;
        }

        if (count > 0) {
            BigDecimal divisor = BigDecimal.valueOf(count);
            survey.setWetWasteKgDay(average(wetSum, divisor));
            survey.setDryWasteKgDay(average(drySum, divisor));
            survey.setGardenHorticultureWasteKgDay(average(gardenSum, divisor));
            survey.setTotalWasteKgDay(average(totalSum, divisor));
            bwgSurveyRepository.save(survey);
        }
    }

    private BigDecimal average(BigDecimal sum, BigDecimal divisor) {
        return sum.divide(divisor, 2, java.math.RoundingMode.HALF_UP);
    }

    private String generateApplicationNo() {
        String year = String.valueOf(Year.now().getValue());
        Random random = new Random();
        for (int attempt = 0; attempt < 20; attempt++) {
            String candidate = "BWG/" + year + "/" + String.format("%05d", random.nextInt(100000));
            if (!bwgSurveyRepository.existsByApplicationNo(candidate)) {
                return candidate;
            }
        }
        // Practically unreachable with a 100000-value space, but fail loudly
        // rather than silently save a survey with no application number.
        throw new IllegalStateException("Could not generate a unique applicationNo after 20 attempts");
    }

    private Integer parseInt(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(value.trim());
        } catch (Exception ex) {
            return null;
        }
    }

    private String capitalize(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }
        String trimmed = value.trim();
        return Character.toUpperCase(trimmed.charAt(0)) + trimmed.substring(1).toLowerCase();
    }
}
