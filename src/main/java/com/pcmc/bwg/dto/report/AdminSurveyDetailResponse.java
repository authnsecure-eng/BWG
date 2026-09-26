package com.pcmc.bwg.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmc.bwg.entity.BwgSurvey;
import com.pcmc.bwg.entity.enums.SurveyStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class AdminSurveyDetailResponse {

    // Application
    private Long id;
    private String applicationNo;
    private SurveyStatus status;
    private String category;
    private Long surveyOfficerId;
    private String surveyOfficerName;
    private OffsetDateTime submittedAt;

    // Eligibility
    private Boolean eligibilityFloorArea;
    private Boolean eligibilityWaterConsumption;
    private Boolean eligibilitySolidWaste;

    // Industrial
    private String industryType;
    private String industryName;
    private String factoryRegistrationNo;

    // Institutional
    private String institutionType;
    private String organizationName;
    private String registrationCinNo;

    // Commercial
    private String establishmentType;
    private String establishmentName;
    private String tradeLicenseNo;

    // Contact
    private String contactPersonName;
    private String designation;
    private String mobileNo;
    private String email;
    private String fullAddress;
    private String pinCode;
    private Integer yearEstablished;

    // Infrastructure
    private BigDecimal plotAreaSqM;
    private BigDecimal builtUpAreaSqM;
    private Integer noOfBuildings;
    private Integer noOfFloors;
    private BigDecimal waterConsumptionLpd;
    private Integer noOfWaterMeters;
    private Boolean municipalWaterConnection;

    // Waste
    private BigDecimal totalWasteKgDay;
    private BigDecimal wetWasteKgDay;
    private BigDecimal dryWasteKgDay;
    private BigDecimal gardenHorticultureWasteKgDay;
    private BigDecimal sanitaryWasteKgDay;
    private BigDecimal biomedicalWasteKgDay;
    private BigDecimal eWasteKgMonth;
    private BigDecimal constructionWasteKgDay;

    // Segregation
    private String binInfrastructure;
    private String wasteSegregatedAtSource;
    private String dryWasteChannelizedTo;

    // Processing
    private Boolean onsiteProcessingAvailable;
    private String processingMethod;
    private BigDecimal processingCapacityKgDay;
    private BigDecimal processingActualKgDay;
    private String productEndUsage;

    // EBWGR
    private Boolean ebwgrRequired;
    private String ebwgrCertificateNo;
    private LocalDate ebwgrValidUntil;

    // Declaration
    private String declarantName;
    private String declarantDesignation;
    private String declarantOrganization;
    private LocalDate declarationDate;
    private String declarationPlace;

    // Acceptance
    private Boolean acceptanceInformationCorrect;
    private Boolean acceptanceSwmRules;
    private Boolean acceptanceInspectionConsent;

    // Location
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String locationAddress;

    // Field Survey
    private String siteCondition;
    private String surveyRemarks;

    // Files
    private String premisesPhotoPath;
    private String waterMeterPhotoPath;
    private String wasteStoragePhotoPath;
    private String ebwgrCertificatePhotoPath;
    private String processingFacilityPhotoPath;
    private String declarationFormPath;
    private String siteOverallPhotoPath;
    private String wasteHandlingPhotoPath;

    // Mobile survey bridge (populated only for surveys submitted through the
    // mobile app - see SurveyIngestService / V9 & V10 migrations. Null for
    // surveys entered directly in the admin panel.)
    private String mobileSurveyId;
    private String zoneNameSnapshot;
    private String ward;
    private String electoralWardName;
    private String subCategoryType;
    private String societyName;
    private String chsRegNo;
    private String ptin;
    private String gstin;
    private String mobileTotalFloors;
    private String mobileTotalUnits;
    private String buildingRemarks;
    private String buildingPermissionRefNo;
    private String buildingPermissionDocPath;
    private String buildingPermissionGeo;
    private String buildingPermissionTime;
    private String waterConsumerNo;
    private String waterBillingPeriod;
    private String waterUnitsConsumed;
    private String waterBillDocPath;
    private String overallDisposalMode;
    private String vendorName;
    private String mouValidity;
    private String processingDestination;
    private String privateVendorDetails;
    private String wasteGivenToOtherAgency;
    private String agencyDocumentPhotoPath;
    private Boolean hasBiogasPlant;
    private String biogasCapacity;
    private String biogasCapacityUnit;
    private String biogasSpaceAvailableSqM;
    private String biogasByProductUsage;
    private String biogasOperationalStatus;
    private String biogasPhotoPath;
    private String biogasRemarks;
    private String geofenceLatitude;
    private String geofenceLongitude;
    private String geofenceRadiusMeters;
    private String geofencePhotoPath;
    private String signagePhotoPath;
    private String premisesPhotoGeo;
    private String premisesPhotoTime;
    private String signagePhotoGeo;
    private String signagePhotoTime;
    private String declarationFileGeo;
    private String declarationFileTime;
    private String surveyorName;
    private String surveyorSelfiePath;
    private String surveyorGps;
    private String surveyorTimestamp;
    private Boolean surveyorVerified;
    private Boolean cpcbCompleted;
    private String cpcbAckNumber;
    private String cpcbSubmissionDate;
    private java.util.List<WasteVisitResponse> wasteVisits;

    // Audit
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static AdminSurveyDetailResponse from(BwgSurvey s) {
        AdminSurveyDetailResponse dto = new AdminSurveyDetailResponse();
        dto.id = s.getId();
        dto.applicationNo = s.getApplicationNo();
        dto.status = s.getStatus();
        dto.category = s.getCategory();
        dto.surveyOfficerId = s.getSurveyOfficerId();
        dto.surveyOfficerName = s.getSurveyOfficerName();
        dto.submittedAt = s.getSubmittedAt();

        dto.eligibilityFloorArea = s.getEligibilityFloorArea();
        dto.eligibilityWaterConsumption = s.getEligibilityWaterConsumption();
        dto.eligibilitySolidWaste = s.getEligibilitySolidWaste();

        dto.industryType = s.getIndustryType();
        dto.industryName = s.getIndustryName();
        dto.factoryRegistrationNo = s.getFactoryRegistrationNo();

        dto.institutionType = s.getInstitutionType();
        dto.organizationName = s.getOrganizationName();
        dto.registrationCinNo = s.getRegistrationCinNo();

        dto.establishmentType = s.getEstablishmentType();
        dto.establishmentName = s.getEstablishmentName();
        dto.tradeLicenseNo = s.getTradeLicenseNo();

        dto.contactPersonName = s.getContactPersonName();
        dto.designation = s.getDesignation();
        dto.mobileNo = s.getMobileNo();
        dto.email = s.getEmail();
        dto.fullAddress = s.getFullAddress();
        dto.pinCode = s.getPinCode();
        dto.yearEstablished = s.getYearEstablished();

        dto.plotAreaSqM = s.getPlotAreaSqM();
        dto.builtUpAreaSqM = s.getBuiltUpAreaSqM();
        dto.noOfBuildings = s.getNoOfBuildings();
        dto.noOfFloors = s.getNoOfFloors();
        dto.waterConsumptionLpd = s.getWaterConsumptionLpd();
        dto.noOfWaterMeters = s.getNoOfWaterMeters();
        dto.municipalWaterConnection = s.getMunicipalWaterConnection();

        dto.totalWasteKgDay = s.getTotalWasteKgDay();
        dto.wetWasteKgDay = s.getWetWasteKgDay();
        dto.dryWasteKgDay = s.getDryWasteKgDay();
        dto.gardenHorticultureWasteKgDay = s.getGardenHorticultureWasteKgDay();
        dto.sanitaryWasteKgDay = s.getSanitaryWasteKgDay();
        dto.biomedicalWasteKgDay = s.getBiomedicalWasteKgDay();
        dto.eWasteKgMonth = s.getEWasteKgMonth();
        dto.constructionWasteKgDay = s.getConstructionWasteKgDay();

        dto.binInfrastructure = s.getBinInfrastructure();
        dto.wasteSegregatedAtSource = s.getWasteSegregatedAtSource();
        dto.dryWasteChannelizedTo = s.getDryWasteChannelizedTo();

        dto.onsiteProcessingAvailable = s.getOnsiteProcessingAvailable();
        dto.processingMethod = s.getProcessingMethod();
        dto.processingCapacityKgDay = s.getProcessingCapacityKgDay();
        dto.processingActualKgDay = s.getProcessingActualKgDay();
        dto.productEndUsage = s.getProductEndUsage();

        dto.ebwgrRequired = s.getEbwgrRequired();
        dto.ebwgrCertificateNo = s.getEbwgrCertificateNo();
        dto.ebwgrValidUntil = s.getEbwgrValidUntil();

        dto.declarantName = s.getDeclarantName();
        dto.declarantDesignation = s.getDeclarantDesignation();
        dto.declarantOrganization = s.getDeclarantOrganization();
        dto.declarationDate = s.getDeclarationDate();
        dto.declarationPlace = s.getDeclarationPlace();

        dto.acceptanceInformationCorrect = s.getAcceptanceInformationCorrect();
        dto.acceptanceSwmRules = s.getAcceptanceSwmRules();
        dto.acceptanceInspectionConsent = s.getAcceptanceInspectionConsent();

        dto.latitude = s.getLatitude();
        dto.longitude = s.getLongitude();
        dto.locationAddress = s.getLocationAddress();

        dto.siteCondition = s.getSiteCondition();
        dto.surveyRemarks = s.getSurveyRemarks();

        dto.premisesPhotoPath = s.getPremisesPhotoPath();
        dto.waterMeterPhotoPath = s.getWaterMeterPhotoPath();
        dto.wasteStoragePhotoPath = s.getWasteStoragePhotoPath();
        dto.ebwgrCertificatePhotoPath = s.getEbwgrCertificatePhotoPath();
        dto.processingFacilityPhotoPath = s.getProcessingFacilityPhotoPath();
        dto.declarationFormPath = s.getDeclarationFormPath();
        dto.siteOverallPhotoPath = s.getSiteOverallPhotoPath();
        dto.wasteHandlingPhotoPath = s.getWasteHandlingPhotoPath();

        dto.mobileSurveyId = s.getMobileSurveyId();
        dto.zoneNameSnapshot = s.getZoneNameSnapshot();
        dto.ward = s.getWard();
        dto.electoralWardName = s.getElectoralWardName();
        dto.subCategoryType = s.getSubCategoryType();
        dto.societyName = s.getSocietyName();
        dto.chsRegNo = s.getChsRegNo();
        dto.ptin = s.getPtin();
        dto.gstin = s.getGstin();
        dto.mobileTotalFloors = s.getMobileTotalFloors();
        dto.mobileTotalUnits = s.getMobileTotalUnits();
        dto.buildingRemarks = s.getBuildingRemarks();
        dto.buildingPermissionRefNo = s.getBuildingPermissionRefNo();
        dto.buildingPermissionDocPath = s.getBuildingPermissionDocPath();
        dto.buildingPermissionGeo = s.getBuildingPermissionGeo();
        dto.buildingPermissionTime = s.getBuildingPermissionTime();
        dto.waterConsumerNo = s.getWaterConsumerNo();
        dto.waterBillingPeriod = s.getWaterBillingPeriod();
        dto.waterUnitsConsumed = s.getWaterUnitsConsumed();
        dto.waterBillDocPath = s.getWaterBillDocPath();
        dto.overallDisposalMode = s.getOverallDisposalMode();
        dto.vendorName = s.getVendorName();
        dto.mouValidity = s.getMouValidity();
        dto.processingDestination = s.getProcessingDestination();
        dto.privateVendorDetails = s.getPrivateVendorDetails();
        dto.wasteGivenToOtherAgency = s.getWasteGivenToOtherAgency();
        dto.agencyDocumentPhotoPath = s.getAgencyDocumentPhotoPath();
        dto.hasBiogasPlant = s.getHasBiogasPlant();
        dto.biogasCapacity = s.getBiogasCapacity();
        dto.biogasCapacityUnit = s.getBiogasCapacityUnit();
        dto.biogasSpaceAvailableSqM = s.getBiogasSpaceAvailableSqM();
        dto.biogasByProductUsage = s.getBiogasByProductUsage();
        dto.biogasOperationalStatus = s.getBiogasOperationalStatus();
        dto.biogasPhotoPath = s.getBiogasPhotoPath();
        dto.biogasRemarks = s.getBiogasRemarks();
        dto.geofenceLatitude = s.getGeofenceLatitude();
        dto.geofenceLongitude = s.getGeofenceLongitude();
        dto.geofenceRadiusMeters = s.getGeofenceRadiusMeters();
        dto.geofencePhotoPath = s.getGeofencePhotoPath();
        dto.signagePhotoPath = s.getSignagePhotoPath();
        dto.premisesPhotoGeo = s.getPremisesPhotoGeo();
        dto.premisesPhotoTime = s.getPremisesPhotoTime();
        dto.signagePhotoGeo = s.getSignagePhotoGeo();
        dto.signagePhotoTime = s.getSignagePhotoTime();
        dto.declarationFileGeo = s.getDeclarationFileGeo();
        dto.declarationFileTime = s.getDeclarationFileTime();
        dto.surveyorName = s.getSurveyorName();
        dto.surveyorSelfiePath = s.getSurveyorSelfiePath();
        dto.surveyorGps = s.getSurveyorGps();
        dto.surveyorTimestamp = s.getSurveyorTimestamp();
        dto.surveyorVerified = s.getSurveyorVerified();
        dto.cpcbCompleted = s.getCpcbCompleted();
        dto.cpcbAckNumber = s.getCpcbAckNumber();
        dto.cpcbSubmissionDate = s.getCpcbSubmissionDate();
        dto.wasteVisits = s.getWasteVisits() == null ? java.util.List.of()
                : s.getWasteVisits().stream().map(WasteVisitResponse::from).toList();

        dto.createdAt = s.getCreatedAt();
        dto.updatedAt = s.getUpdatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public SurveyStatus getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }

    public Long getSurveyOfficerId() {
        return surveyOfficerId;
    }

    public String getSurveyOfficerName() {
        return surveyOfficerName;
    }

    public OffsetDateTime getSubmittedAt() {
        return submittedAt;
    }

    public Boolean getEligibilityFloorArea() {
        return eligibilityFloorArea;
    }

    public Boolean getEligibilityWaterConsumption() {
        return eligibilityWaterConsumption;
    }

    public Boolean getEligibilitySolidWaste() {
        return eligibilitySolidWaste;
    }

    public String getIndustryType() {
        return industryType;
    }

    public String getIndustryName() {
        return industryName;
    }

    public String getFactoryRegistrationNo() {
        return factoryRegistrationNo;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getRegistrationCinNo() {
        return registrationCinNo;
    }

    public String getEstablishmentType() {
        return establishmentType;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public String getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public String getDesignation() {
        return designation;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public String getPinCode() {
        return pinCode;
    }

    public Integer getYearEstablished() {
        return yearEstablished;
    }

    public BigDecimal getPlotAreaSqM() {
        return plotAreaSqM;
    }

    public BigDecimal getBuiltUpAreaSqM() {
        return builtUpAreaSqM;
    }

    public Integer getNoOfBuildings() {
        return noOfBuildings;
    }

    public Integer getNoOfFloors() {
        return noOfFloors;
    }

    public BigDecimal getWaterConsumptionLpd() {
        return waterConsumptionLpd;
    }

    public Integer getNoOfWaterMeters() {
        return noOfWaterMeters;
    }

    public Boolean getMunicipalWaterConnection() {
        return municipalWaterConnection;
    }

    public BigDecimal getTotalWasteKgDay() {
        return totalWasteKgDay;
    }

    public BigDecimal getWetWasteKgDay() {
        return wetWasteKgDay;
    }

    public BigDecimal getDryWasteKgDay() {
        return dryWasteKgDay;
    }

    public BigDecimal getGardenHorticultureWasteKgDay() {
        return gardenHorticultureWasteKgDay;
    }

    public BigDecimal getSanitaryWasteKgDay() {
        return sanitaryWasteKgDay;
    }

    public BigDecimal getBiomedicalWasteKgDay() {
        return biomedicalWasteKgDay;
    }

    @JsonProperty("eWasteKgMonth")
    public BigDecimal getEWasteKgMonth() {
        return eWasteKgMonth;
    }

    public BigDecimal getConstructionWasteKgDay() {
        return constructionWasteKgDay;
    }

    public String getBinInfrastructure() {
        return binInfrastructure;
    }

    public String getWasteSegregatedAtSource() {
        return wasteSegregatedAtSource;
    }

    public String getDryWasteChannelizedTo() {
        return dryWasteChannelizedTo;
    }

    public Boolean getOnsiteProcessingAvailable() {
        return onsiteProcessingAvailable;
    }

    public String getProcessingMethod() {
        return processingMethod;
    }

    public BigDecimal getProcessingCapacityKgDay() {
        return processingCapacityKgDay;
    }

    public BigDecimal getProcessingActualKgDay() {
        return processingActualKgDay;
    }

    public String getProductEndUsage() {
        return productEndUsage;
    }

    public Boolean getEbwgrRequired() {
        return ebwgrRequired;
    }

    public String getEbwgrCertificateNo() {
        return ebwgrCertificateNo;
    }

    public LocalDate getEbwgrValidUntil() {
        return ebwgrValidUntil;
    }

    public String getDeclarantName() {
        return declarantName;
    }

    public String getDeclarantDesignation() {
        return declarantDesignation;
    }

    public String getDeclarantOrganization() {
        return declarantOrganization;
    }

    public LocalDate getDeclarationDate() {
        return declarationDate;
    }

    public String getDeclarationPlace() {
        return declarationPlace;
    }

    public Boolean getAcceptanceInformationCorrect() {
        return acceptanceInformationCorrect;
    }

    public Boolean getAcceptanceSwmRules() {
        return acceptanceSwmRules;
    }

    public Boolean getAcceptanceInspectionConsent() {
        return acceptanceInspectionConsent;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getSiteCondition() {
        return siteCondition;
    }

    public String getSurveyRemarks() {
        return surveyRemarks;
    }

    public String getPremisesPhotoPath() {
        return premisesPhotoPath;
    }

    public String getWaterMeterPhotoPath() {
        return waterMeterPhotoPath;
    }

    public String getWasteStoragePhotoPath() {
        return wasteStoragePhotoPath;
    }

    public String getEbwgrCertificatePhotoPath() {
        return ebwgrCertificatePhotoPath;
    }

    public String getProcessingFacilityPhotoPath() {
        return processingFacilityPhotoPath;
    }

    public String getDeclarationFormPath() {
        return declarationFormPath;
    }

    public String getSiteOverallPhotoPath() {
        return siteOverallPhotoPath;
    }

    public String getWasteHandlingPhotoPath() {
        return wasteHandlingPhotoPath;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getMobileSurveyId() { return mobileSurveyId; }

    public String getZoneNameSnapshot() { return zoneNameSnapshot; }

    public String getWard() { return ward; }

    public String getElectoralWardName() { return electoralWardName; }

    public String getSubCategoryType() { return subCategoryType; }

    public String getSocietyName() { return societyName; }

    public String getChsRegNo() { return chsRegNo; }

    public String getPtin() { return ptin; }

    public String getGstin() { return gstin; }

    public String getMobileTotalFloors() { return mobileTotalFloors; }

    public String getMobileTotalUnits() { return mobileTotalUnits; }

    public String getBuildingRemarks() { return buildingRemarks; }

    public String getBuildingPermissionRefNo() { return buildingPermissionRefNo; }

    public String getBuildingPermissionDocPath() { return buildingPermissionDocPath; }

    public String getBuildingPermissionGeo() { return buildingPermissionGeo; }

    public String getBuildingPermissionTime() { return buildingPermissionTime; }

    public String getWaterConsumerNo() { return waterConsumerNo; }

    public String getWaterBillingPeriod() { return waterBillingPeriod; }

    public String getWaterUnitsConsumed() { return waterUnitsConsumed; }

    public String getWaterBillDocPath() { return waterBillDocPath; }

    public String getOverallDisposalMode() { return overallDisposalMode; }

    public String getVendorName() { return vendorName; }

    public String getMouValidity() { return mouValidity; }

    public String getProcessingDestination() { return processingDestination; }

    public String getPrivateVendorDetails() { return privateVendorDetails; }

    public String getWasteGivenToOtherAgency() { return wasteGivenToOtherAgency; }

    public String getAgencyDocumentPhotoPath() { return agencyDocumentPhotoPath; }

    public Boolean getHasBiogasPlant() { return hasBiogasPlant; }

    public String getBiogasCapacity() { return biogasCapacity; }

    public String getBiogasCapacityUnit() { return biogasCapacityUnit; }

    public String getBiogasSpaceAvailableSqM() { return biogasSpaceAvailableSqM; }

    public String getBiogasByProductUsage() { return biogasByProductUsage; }

    public String getBiogasOperationalStatus() { return biogasOperationalStatus; }

    public String getBiogasPhotoPath() { return biogasPhotoPath; }

    public String getBiogasRemarks() { return biogasRemarks; }

    public String getGeofenceLatitude() { return geofenceLatitude; }

    public String getGeofenceLongitude() { return geofenceLongitude; }

    public String getGeofenceRadiusMeters() { return geofenceRadiusMeters; }

    public String getGeofencePhotoPath() { return geofencePhotoPath; }

    public String getSignagePhotoPath() { return signagePhotoPath; }

    public String getPremisesPhotoGeo() { return premisesPhotoGeo; }

    public String getPremisesPhotoTime() { return premisesPhotoTime; }

    public String getSignagePhotoGeo() { return signagePhotoGeo; }

    public String getSignagePhotoTime() { return signagePhotoTime; }

    public String getDeclarationFileGeo() { return declarationFileGeo; }

    public String getDeclarationFileTime() { return declarationFileTime; }

    public String getSurveyorName() { return surveyorName; }

    public String getSurveyorSelfiePath() { return surveyorSelfiePath; }

    public String getSurveyorGps() { return surveyorGps; }

    public String getSurveyorTimestamp() { return surveyorTimestamp; }

    public Boolean getSurveyorVerified() { return surveyorVerified; }

    public Boolean getCpcbCompleted() { return cpcbCompleted; }

    public String getCpcbAckNumber() { return cpcbAckNumber; }

    public String getCpcbSubmissionDate() { return cpcbSubmissionDate; }

    public java.util.List<WasteVisitResponse> getWasteVisits() { return wasteVisits; }

    /** One day's waste weighment, mirroring BwgSurveyWasteVisit - only present for mobile-submitted surveys. */
    public static class WasteVisitResponse {
        private Integer dayNumber;
        private String visitDate;
        private BigDecimal wetWasteKg;
        private BigDecimal dryWasteKg;
        private BigDecimal gardenWasteKg;
        private BigDecimal totalWasteKg;
        private String wetWastePhotoPath;
        private String dryWastePhotoPath;
        private String gardenWastePhotoPath;
        private String weighingScalePhotoPath;
        private String handoverAreaPhotoPath;
        private Boolean isCompleted;

        static WasteVisitResponse from(com.pcmc.bwg.entity.BwgSurveyWasteVisit v) {
            WasteVisitResponse r = new WasteVisitResponse();
            r.dayNumber = v.getDayNumber();
            r.visitDate = v.getVisitDate();
            r.wetWasteKg = v.getWetWasteKg();
            r.dryWasteKg = v.getDryWasteKg();
            r.gardenWasteKg = v.getGardenWasteKg();
            r.totalWasteKg = v.getTotalWasteKg();
            r.wetWastePhotoPath = v.getWetWastePhotoPath();
            r.dryWastePhotoPath = v.getDryWastePhotoPath();
            r.gardenWastePhotoPath = v.getGardenWastePhotoPath();
            r.weighingScalePhotoPath = v.getWeighingScalePhotoPath();
            r.handoverAreaPhotoPath = v.getHandoverAreaPhotoPath();
            r.isCompleted = v.getIsCompleted();
            return r;
        }

        public Integer getDayNumber() { return dayNumber; }
        public String getVisitDate() { return visitDate; }
        public BigDecimal getWetWasteKg() { return wetWasteKg; }
        public BigDecimal getDryWasteKg() { return dryWasteKg; }
        public BigDecimal getGardenWasteKg() { return gardenWasteKg; }
        public BigDecimal getTotalWasteKg() { return totalWasteKg; }
        public String getWetWastePhotoPath() { return wetWastePhotoPath; }
        public String getDryWastePhotoPath() { return dryWastePhotoPath; }
        public String getGardenWastePhotoPath() { return gardenWastePhotoPath; }
        public String getWeighingScalePhotoPath() { return weighingScalePhotoPath; }
        public String getHandoverAreaPhotoPath() { return handoverAreaPhotoPath; }
        public Boolean getIsCompleted() { return isCompleted; }
    }
}
