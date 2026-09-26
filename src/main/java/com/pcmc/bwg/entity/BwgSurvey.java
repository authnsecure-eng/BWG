package com.pcmc.bwg.entity;

import com.pcmc.bwg.entity.enums.SurveyStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bwg_survey")
public class BwgSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // --- bridge fields: populated from the mobile app's survey submission ---

    @Column(name = "mobile_survey_id", length = 50)
    private String mobileSurveyId;

    @Column(name = "sub_category_type", length = 150)
    private String subCategoryType;

    @Column(name = "society_name")
    private String societyName;

    @Column(name = "chs_reg_no", length = 100)
    private String chsRegNo;

    @Column(name = "ptin", length = 100)
    private String ptin;

    @Column(name = "gstin", length = 100)
    private String gstin;

    @Column(name = "mobile_total_floors", length = 20)
    private String mobileTotalFloors;

    @Column(name = "mobile_total_units", length = 20)
    private String mobileTotalUnits;

    @Column(name = "building_remarks", columnDefinition = "TEXT")
    private String buildingRemarks;

    @Column(name = "building_permission_ref_no", length = 100)
    private String buildingPermissionRefNo;

    @Column(name = "building_permission_doc_path", length = 500)
    private String buildingPermissionDocPath;

    @Column(name = "building_permission_geo", length = 255)
    private String buildingPermissionGeo;

    @Column(name = "building_permission_time", length = 100)
    private String buildingPermissionTime;

    @Column(name = "water_consumer_no", length = 100)
    private String waterConsumerNo;

    @Column(name = "water_billing_period", length = 100)
    private String waterBillingPeriod;

    @Column(name = "water_units_consumed", length = 100)
    private String waterUnitsConsumed;

    @Column(name = "water_bill_doc_path", length = 500)
    private String waterBillDocPath;

    @Column(name = "overall_disposal_mode", length = 100)
    private String overallDisposalMode;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "mou_validity", length = 100)
    private String mouValidity;

    @Column(name = "processing_destination")
    private String processingDestination;

    @Column(name = "private_vendor_details", length = 500)
    private String privateVendorDetails;

    @Column(name = "waste_given_to_other_agency", length = 255)
    private String wasteGivenToOtherAgency;

    @Column(name = "agency_document_photo_path", length = 500)
    private String agencyDocumentPhotoPath;

    @Column(name = "has_biogas_plant")
    private Boolean hasBiogasPlant = false;

    @Column(name = "biogas_capacity", length = 50)
    private String biogasCapacity;

    @Column(name = "biogas_capacity_unit", length = 50)
    private String biogasCapacityUnit;

    @Column(name = "biogas_space_available_sq_m", length = 50)
    private String biogasSpaceAvailableSqM;

    @Column(name = "biogas_by_product_usage")
    private String biogasByProductUsage;

    @Column(name = "biogas_operational_status", length = 50)
    private String biogasOperationalStatus;

    @Column(name = "biogas_photo_path", length = 500)
    private String biogasPhotoPath;

    @Column(name = "biogas_remarks", columnDefinition = "TEXT")
    private String biogasRemarks;

    @Column(name = "geofence_latitude", length = 50)
    private String geofenceLatitude;

    @Column(name = "geofence_longitude", length = 50)
    private String geofenceLongitude;

    @Column(name = "geofence_radius_meters", length = 50)
    private String geofenceRadiusMeters;

    @Column(name = "geofence_photo_path", length = 500)
    private String geofencePhotoPath;

    @Column(name = "signage_photo_path", length = 500)
    private String signagePhotoPath;

    @Column(name = "premises_photo_geo", length = 255)
    private String premisesPhotoGeo;

    @Column(name = "premises_photo_time", length = 100)
    private String premisesPhotoTime;

    @Column(name = "signage_photo_geo", length = 255)
    private String signagePhotoGeo;

    @Column(name = "signage_photo_time", length = 100)
    private String signagePhotoTime;

    @Column(name = "declaration_file_geo", length = 255)
    private String declarationFileGeo;

    @Column(name = "declaration_file_time", length = 100)
    private String declarationFileTime;

    @Column(name = "surveyor_name", length = 150)
    private String surveyorName;

    @Column(name = "surveyor_selfie_path", length = 500)
    private String surveyorSelfiePath;

    @Column(name = "surveyor_gps", length = 255)
    private String surveyorGps;

    @Column(name = "surveyor_timestamp", length = 100)
    private String surveyorTimestamp;

    @Column(name = "surveyor_verified")
    private Boolean surveyorVerified = false;

    @Column(name = "cpcb_completed")
    private Boolean cpcbCompleted = false;

    @Column(name = "cpcb_ack_number", length = 100)
    private String cpcbAckNumber;

    @Column(name = "cpcb_submission_date", length = 50)
    private String cpcbSubmissionDate;

    @Column(name = "electoral_ward_name", length = 100)
    private String electoralWardName;

    @OneToMany(mappedBy = "bwgSurvey", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BwgSurveyWasteVisit> wasteVisits = new ArrayList<>();

    @Column(name = "application_no", nullable = false, unique = true, length = 30)
    private String applicationNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private SurveyStatus status;

    @Column(name = "category", nullable = false, length = 30)
    private String category;

    @Column(name = "survey_officer_id")
    private Long surveyOfficerId;

    @Column(name = "survey_officer_name", length = 150)
    private String surveyOfficerName;

    @Column(name = "eligibility_floor_area")
    private Boolean eligibilityFloorArea;

    @Column(name = "eligibility_water_consumption")
    private Boolean eligibilityWaterConsumption;

    @Column(name = "eligibility_solid_waste")
    private Boolean eligibilitySolidWaste;

    @Column(name = "industry_type", length = 150)
    private String industryType;

    @Column(name = "industry_name", length = 255)
    private String industryName;

    @Column(name = "factory_registration_no", length = 100)
    private String factoryRegistrationNo;

    @Column(name = "institution_type", length = 150)
    private String institutionType;

    @Column(name = "organization_name", length = 255)
    private String organizationName;

    @Column(name = "registration_cin_no", length = 100)
    private String registrationCinNo;

    @Column(name = "establishment_type", length = 150)
    private String establishmentType;

    @Column(name = "establishment_name", length = 255)
    private String establishmentName;

    @Column(name = "trade_license_no", length = 100)
    private String tradeLicenseNo;

    @Column(name = "contact_person_name", nullable = false, length = 150)
    private String contactPersonName;

    @Column(name = "designation", nullable = false, length = 100)
    private String designation;

    @Column(name = "mobile_no", nullable = false, length = 15)
    private String mobileNo;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "full_address", nullable = false)
    private String fullAddress;

    @Column(name = "pin_code", length = 10)
    private String pinCode;

    @Column(name = "year_established")
    private Integer yearEstablished;

    @Column(name = "plot_area_sq_m", precision = 12, scale = 2)
    private BigDecimal plotAreaSqM;

    @Column(name = "built_up_area_sq_m", precision = 12, scale = 2)
    private BigDecimal builtUpAreaSqM;

    @Column(name = "no_of_buildings")
    private Integer noOfBuildings;

    @Column(name = "no_of_floors")
    private Integer noOfFloors;

    @Column(name = "water_consumption_lpd", precision = 12, scale = 2)
    private BigDecimal waterConsumptionLpd;

    @Column(name = "no_of_water_meters")
    private Integer noOfWaterMeters;

    @Column(name = "municipal_water_connection")
    private Boolean municipalWaterConnection;

    @Column(name = "total_waste_kg_day", precision = 12, scale = 2)
    private BigDecimal totalWasteKgDay;

    @Column(name = "wet_waste_kg_day", precision = 12, scale = 2)
    private BigDecimal wetWasteKgDay;

    @Column(name = "dry_waste_kg_day", precision = 12, scale = 2)
    private BigDecimal dryWasteKgDay;

    @Column(name = "garden_horticulture_waste_kg_day", precision = 12, scale = 2)
    private BigDecimal gardenHorticultureWasteKgDay;

    @Column(name = "sanitary_waste_kg_day", precision = 12, scale = 2)
    private BigDecimal sanitaryWasteKgDay;

    @Column(name = "biomedical_waste_kg_day", precision = 12, scale = 2)
    private BigDecimal biomedicalWasteKgDay;

    @Column(name = "e_waste_kg_month", precision = 12, scale = 2)
    private BigDecimal eWasteKgMonth;

    @Column(name = "construction_waste_kg_day", precision = 12, scale = 2)
    private BigDecimal constructionWasteKgDay;

    @Column(name = "bin_infrastructure", length = 20)
    private String binInfrastructure;

    @Column(name = "waste_segregated_at_source", length = 20)
    private String wasteSegregatedAtSource;

    @Column(name = "dry_waste_channelized_to", length = 100)
    private String dryWasteChannelizedTo;

    @Column(name = "onsite_processing_available")
    private Boolean onsiteProcessingAvailable;

    @Column(name = "processing_method", length = 100)
    private String processingMethod;

    @Column(name = "processing_capacity_kg_day", precision = 12, scale = 2)
    private BigDecimal processingCapacityKgDay;

    @Column(name = "processing_actual_kg_day", precision = 12, scale = 2)
    private BigDecimal processingActualKgDay;

    @Column(name = "product_end_usage", length = 100)
    private String productEndUsage;

    @Column(name = "ebwgr_required")
    private Boolean ebwgrRequired;

    @Column(name = "ebwgr_certificate_no", length = 100)
    private String ebwgrCertificateNo;

    @Column(name = "ebwgr_valid_until")
    private LocalDate ebwgrValidUntil;

    @Column(name = "declarant_name", length = 150)
    private String declarantName;

    @Column(name = "declarant_designation", length = 100)
    private String declarantDesignation;

    @Column(name = "declarant_organization", length = 255)
    private String declarantOrganization;

    @Column(name = "declaration_date")
    private LocalDate declarationDate;

    @Column(name = "declaration_place", length = 150)
    private String declarationPlace;

    @Column(name = "acceptance_information_correct")
    private Boolean acceptanceInformationCorrect;

    @Column(name = "acceptance_swm_rules")
    private Boolean acceptanceSwmRules;

    @Column(name = "acceptance_inspection_consent")
    private Boolean acceptanceInspectionConsent;

    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "location_address")
    private String locationAddress;

    @Column(name = "site_condition", length = 20)
    private String siteCondition;

    @Column(name = "survey_remarks")
    private String surveyRemarks;

    @Column(name = "premises_photo_path")
    private String premisesPhotoPath;

    @Column(name = "water_meter_photo_path")
    private String waterMeterPhotoPath;

    @Column(name = "waste_storage_photo_path")
    private String wasteStoragePhotoPath;

    @Column(name = "ebwgr_certificate_photo_path")
    private String ebwgrCertificatePhotoPath;

    @Column(name = "processing_facility_photo_path")
    private String processingFacilityPhotoPath;

    @Column(name = "declaration_form_path")
    private String declarationFormPath;

    @Column(name = "site_overall_photo_path")
    private String siteOverallPhotoPath;

    @Column(name = "waste_handling_photo_path")
    private String wasteHandlingPhotoPath;

    @Column(name = "zone_id")
    private Long zoneId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", insertable = false, updatable = false)
    private Zone zoneRef;

    @Column(name = "zone_name_snapshot", length = 50)
    private String zoneNameSnapshot;

    @Column(name = "ward", length = 50)
    private String ward;

    @Column(name = "submitted_at")
    private OffsetDateTime submittedAt;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public SurveyStatus getStatus() {
        return status;
    }

    public void setStatus(SurveyStatus status) {
        this.status = status;
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

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Zone getZoneRef() {
        return zoneRef;
    }

    public String getZoneNameSnapshot() {
        return zoneNameSnapshot;
    }

    public String getWard() {
        return ward;
    }

    public OffsetDateTime getSubmittedAt() {
        return submittedAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // --- setters added for mobile-survey ingest mapping ---

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSurveyOfficerId(Long surveyOfficerId) {
        this.surveyOfficerId = surveyOfficerId;
    }

    public void setSurveyOfficerName(String surveyOfficerName) {
        this.surveyOfficerName = surveyOfficerName;
    }

    public void setEligibilityFloorArea(Boolean eligibilityFloorArea) {
        this.eligibilityFloorArea = eligibilityFloorArea;
    }

    public void setEligibilityWaterConsumption(Boolean eligibilityWaterConsumption) {
        this.eligibilityWaterConsumption = eligibilityWaterConsumption;
    }

    public void setEligibilitySolidWaste(Boolean eligibilitySolidWaste) {
        this.eligibilitySolidWaste = eligibilitySolidWaste;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public void setFactoryRegistrationNo(String factoryRegistrationNo) {
        this.factoryRegistrationNo = factoryRegistrationNo;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public void setRegistrationCinNo(String registrationCinNo) {
        this.registrationCinNo = registrationCinNo;
    }

    public void setEstablishmentType(String establishmentType) {
        this.establishmentType = establishmentType;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public void setTradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public void setYearEstablished(Integer yearEstablished) {
        this.yearEstablished = yearEstablished;
    }

    public void setPlotAreaSqM(BigDecimal plotAreaSqM) {
        this.plotAreaSqM = plotAreaSqM;
    }

    public void setBuiltUpAreaSqM(BigDecimal builtUpAreaSqM) {
        this.builtUpAreaSqM = builtUpAreaSqM;
    }

    public void setNoOfBuildings(Integer noOfBuildings) {
        this.noOfBuildings = noOfBuildings;
    }

    public void setNoOfFloors(Integer noOfFloors) {
        this.noOfFloors = noOfFloors;
    }

    public void setWaterConsumptionLpd(BigDecimal waterConsumptionLpd) {
        this.waterConsumptionLpd = waterConsumptionLpd;
    }

    public void setNoOfWaterMeters(Integer noOfWaterMeters) {
        this.noOfWaterMeters = noOfWaterMeters;
    }

    public void setMunicipalWaterConnection(Boolean municipalWaterConnection) {
        this.municipalWaterConnection = municipalWaterConnection;
    }

    public void setTotalWasteKgDay(BigDecimal totalWasteKgDay) {
        this.totalWasteKgDay = totalWasteKgDay;
    }

    public void setWetWasteKgDay(BigDecimal wetWasteKgDay) {
        this.wetWasteKgDay = wetWasteKgDay;
    }

    public void setDryWasteKgDay(BigDecimal dryWasteKgDay) {
        this.dryWasteKgDay = dryWasteKgDay;
    }

    public void setGardenHorticultureWasteKgDay(BigDecimal gardenHorticultureWasteKgDay) {
        this.gardenHorticultureWasteKgDay = gardenHorticultureWasteKgDay;
    }

    public void setSanitaryWasteKgDay(BigDecimal sanitaryWasteKgDay) {
        this.sanitaryWasteKgDay = sanitaryWasteKgDay;
    }

    public void setBiomedicalWasteKgDay(BigDecimal biomedicalWasteKgDay) {
        this.biomedicalWasteKgDay = biomedicalWasteKgDay;
    }

    public void setEWasteKgMonth(BigDecimal eWasteKgMonth) {
        this.eWasteKgMonth = eWasteKgMonth;
    }

    public void setConstructionWasteKgDay(BigDecimal constructionWasteKgDay) {
        this.constructionWasteKgDay = constructionWasteKgDay;
    }

    public void setBinInfrastructure(String binInfrastructure) {
        this.binInfrastructure = binInfrastructure;
    }

    public void setWasteSegregatedAtSource(String wasteSegregatedAtSource) {
        this.wasteSegregatedAtSource = wasteSegregatedAtSource;
    }

    public void setDryWasteChannelizedTo(String dryWasteChannelizedTo) {
        this.dryWasteChannelizedTo = dryWasteChannelizedTo;
    }

    public void setOnsiteProcessingAvailable(Boolean onsiteProcessingAvailable) {
        this.onsiteProcessingAvailable = onsiteProcessingAvailable;
    }

    public void setProcessingMethod(String processingMethod) {
        this.processingMethod = processingMethod;
    }

    public void setProcessingCapacityKgDay(BigDecimal processingCapacityKgDay) {
        this.processingCapacityKgDay = processingCapacityKgDay;
    }

    public void setProcessingActualKgDay(BigDecimal processingActualKgDay) {
        this.processingActualKgDay = processingActualKgDay;
    }

    public void setProductEndUsage(String productEndUsage) {
        this.productEndUsage = productEndUsage;
    }

    public void setEbwgrRequired(Boolean ebwgrRequired) {
        this.ebwgrRequired = ebwgrRequired;
    }

    public void setEbwgrCertificateNo(String ebwgrCertificateNo) {
        this.ebwgrCertificateNo = ebwgrCertificateNo;
    }

    public void setEbwgrValidUntil(LocalDate ebwgrValidUntil) {
        this.ebwgrValidUntil = ebwgrValidUntil;
    }

    public void setDeclarantName(String declarantName) {
        this.declarantName = declarantName;
    }

    public void setDeclarantDesignation(String declarantDesignation) {
        this.declarantDesignation = declarantDesignation;
    }

    public void setDeclarantOrganization(String declarantOrganization) {
        this.declarantOrganization = declarantOrganization;
    }

    public void setDeclarationDate(LocalDate declarationDate) {
        this.declarationDate = declarationDate;
    }

    public void setDeclarationPlace(String declarationPlace) {
        this.declarationPlace = declarationPlace;
    }

    public void setAcceptanceInformationCorrect(Boolean acceptanceInformationCorrect) {
        this.acceptanceInformationCorrect = acceptanceInformationCorrect;
    }

    public void setAcceptanceSwmRules(Boolean acceptanceSwmRules) {
        this.acceptanceSwmRules = acceptanceSwmRules;
    }

    public void setAcceptanceInspectionConsent(Boolean acceptanceInspectionConsent) {
        this.acceptanceInspectionConsent = acceptanceInspectionConsent;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public void setSiteCondition(String siteCondition) {
        this.siteCondition = siteCondition;
    }

    public void setSurveyRemarks(String surveyRemarks) {
        this.surveyRemarks = surveyRemarks;
    }

    public void setPremisesPhotoPath(String premisesPhotoPath) {
        this.premisesPhotoPath = premisesPhotoPath;
    }

    public void setWaterMeterPhotoPath(String waterMeterPhotoPath) {
        this.waterMeterPhotoPath = waterMeterPhotoPath;
    }

    public void setWasteStoragePhotoPath(String wasteStoragePhotoPath) {
        this.wasteStoragePhotoPath = wasteStoragePhotoPath;
    }

    public void setEbwgrCertificatePhotoPath(String ebwgrCertificatePhotoPath) {
        this.ebwgrCertificatePhotoPath = ebwgrCertificatePhotoPath;
    }

    public void setProcessingFacilityPhotoPath(String processingFacilityPhotoPath) {
        this.processingFacilityPhotoPath = processingFacilityPhotoPath;
    }

    public void setDeclarationFormPath(String declarationFormPath) {
        this.declarationFormPath = declarationFormPath;
    }

    public void setSiteOverallPhotoPath(String siteOverallPhotoPath) {
        this.siteOverallPhotoPath = siteOverallPhotoPath;
    }

    public void setWasteHandlingPhotoPath(String wasteHandlingPhotoPath) {
        this.wasteHandlingPhotoPath = wasteHandlingPhotoPath;
    }

    public void setZoneNameSnapshot(String zoneNameSnapshot) {
        this.zoneNameSnapshot = zoneNameSnapshot;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public void setSubmittedAt(OffsetDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }


    // --- bridge field accessors ---

    public String getMobileSurveyId() { return mobileSurveyId; }
    public void setMobileSurveyId(String mobileSurveyId) { this.mobileSurveyId = mobileSurveyId; }

    public String getSubCategoryType() { return subCategoryType; }
    public void setSubCategoryType(String subCategoryType) { this.subCategoryType = subCategoryType; }

    public String getSocietyName() { return societyName; }
    public void setSocietyName(String societyName) { this.societyName = societyName; }

    public String getChsRegNo() { return chsRegNo; }
    public void setChsRegNo(String chsRegNo) { this.chsRegNo = chsRegNo; }

    public String getPtin() { return ptin; }
    public void setPtin(String ptin) { this.ptin = ptin; }

    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }

    public String getMobileTotalFloors() { return mobileTotalFloors; }
    public void setMobileTotalFloors(String mobileTotalFloors) { this.mobileTotalFloors = mobileTotalFloors; }

    public String getMobileTotalUnits() { return mobileTotalUnits; }
    public void setMobileTotalUnits(String mobileTotalUnits) { this.mobileTotalUnits = mobileTotalUnits; }

    public String getBuildingRemarks() { return buildingRemarks; }
    public void setBuildingRemarks(String buildingRemarks) { this.buildingRemarks = buildingRemarks; }

    public String getBuildingPermissionRefNo() { return buildingPermissionRefNo; }
    public void setBuildingPermissionRefNo(String buildingPermissionRefNo) { this.buildingPermissionRefNo = buildingPermissionRefNo; }

    public String getBuildingPermissionDocPath() { return buildingPermissionDocPath; }
    public void setBuildingPermissionDocPath(String buildingPermissionDocPath) { this.buildingPermissionDocPath = buildingPermissionDocPath; }

    public String getBuildingPermissionGeo() { return buildingPermissionGeo; }
    public void setBuildingPermissionGeo(String buildingPermissionGeo) { this.buildingPermissionGeo = buildingPermissionGeo; }

    public String getBuildingPermissionTime() { return buildingPermissionTime; }
    public void setBuildingPermissionTime(String buildingPermissionTime) { this.buildingPermissionTime = buildingPermissionTime; }

    public String getWaterConsumerNo() { return waterConsumerNo; }
    public void setWaterConsumerNo(String waterConsumerNo) { this.waterConsumerNo = waterConsumerNo; }

    public String getWaterBillingPeriod() { return waterBillingPeriod; }
    public void setWaterBillingPeriod(String waterBillingPeriod) { this.waterBillingPeriod = waterBillingPeriod; }

    public String getWaterUnitsConsumed() { return waterUnitsConsumed; }
    public void setWaterUnitsConsumed(String waterUnitsConsumed) { this.waterUnitsConsumed = waterUnitsConsumed; }

    public String getWaterBillDocPath() { return waterBillDocPath; }
    public void setWaterBillDocPath(String waterBillDocPath) { this.waterBillDocPath = waterBillDocPath; }

    public String getOverallDisposalMode() { return overallDisposalMode; }
    public void setOverallDisposalMode(String overallDisposalMode) { this.overallDisposalMode = overallDisposalMode; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public String getMouValidity() { return mouValidity; }
    public void setMouValidity(String mouValidity) { this.mouValidity = mouValidity; }

    public String getProcessingDestination() { return processingDestination; }
    public void setProcessingDestination(String processingDestination) { this.processingDestination = processingDestination; }

    public String getPrivateVendorDetails() { return privateVendorDetails; }
    public void setPrivateVendorDetails(String privateVendorDetails) { this.privateVendorDetails = privateVendorDetails; }

    public String getWasteGivenToOtherAgency() { return wasteGivenToOtherAgency; }
    public void setWasteGivenToOtherAgency(String wasteGivenToOtherAgency) { this.wasteGivenToOtherAgency = wasteGivenToOtherAgency; }

    public String getAgencyDocumentPhotoPath() { return agencyDocumentPhotoPath; }
    public void setAgencyDocumentPhotoPath(String agencyDocumentPhotoPath) { this.agencyDocumentPhotoPath = agencyDocumentPhotoPath; }

    public Boolean getHasBiogasPlant() { return hasBiogasPlant; }
    public void setHasBiogasPlant(Boolean hasBiogasPlant) { this.hasBiogasPlant = hasBiogasPlant; }

    public String getBiogasCapacity() { return biogasCapacity; }
    public void setBiogasCapacity(String biogasCapacity) { this.biogasCapacity = biogasCapacity; }

    public String getBiogasCapacityUnit() { return biogasCapacityUnit; }
    public void setBiogasCapacityUnit(String biogasCapacityUnit) { this.biogasCapacityUnit = biogasCapacityUnit; }

    public String getBiogasSpaceAvailableSqM() { return biogasSpaceAvailableSqM; }
    public void setBiogasSpaceAvailableSqM(String biogasSpaceAvailableSqM) { this.biogasSpaceAvailableSqM = biogasSpaceAvailableSqM; }

    public String getBiogasByProductUsage() { return biogasByProductUsage; }
    public void setBiogasByProductUsage(String biogasByProductUsage) { this.biogasByProductUsage = biogasByProductUsage; }

    public String getBiogasOperationalStatus() { return biogasOperationalStatus; }
    public void setBiogasOperationalStatus(String biogasOperationalStatus) { this.biogasOperationalStatus = biogasOperationalStatus; }

    public String getBiogasPhotoPath() { return biogasPhotoPath; }
    public void setBiogasPhotoPath(String biogasPhotoPath) { this.biogasPhotoPath = biogasPhotoPath; }

    public String getBiogasRemarks() { return biogasRemarks; }
    public void setBiogasRemarks(String biogasRemarks) { this.biogasRemarks = biogasRemarks; }

    public String getGeofenceLatitude() { return geofenceLatitude; }
    public void setGeofenceLatitude(String geofenceLatitude) { this.geofenceLatitude = geofenceLatitude; }

    public String getGeofenceLongitude() { return geofenceLongitude; }
    public void setGeofenceLongitude(String geofenceLongitude) { this.geofenceLongitude = geofenceLongitude; }

    public String getGeofenceRadiusMeters() { return geofenceRadiusMeters; }
    public void setGeofenceRadiusMeters(String geofenceRadiusMeters) { this.geofenceRadiusMeters = geofenceRadiusMeters; }

    public String getGeofencePhotoPath() { return geofencePhotoPath; }
    public void setGeofencePhotoPath(String geofencePhotoPath) { this.geofencePhotoPath = geofencePhotoPath; }

    public String getSignagePhotoPath() { return signagePhotoPath; }
    public void setSignagePhotoPath(String signagePhotoPath) { this.signagePhotoPath = signagePhotoPath; }

    public String getPremisesPhotoGeo() { return premisesPhotoGeo; }
    public void setPremisesPhotoGeo(String premisesPhotoGeo) { this.premisesPhotoGeo = premisesPhotoGeo; }

    public String getPremisesPhotoTime() { return premisesPhotoTime; }
    public void setPremisesPhotoTime(String premisesPhotoTime) { this.premisesPhotoTime = premisesPhotoTime; }

    public String getSignagePhotoGeo() { return signagePhotoGeo; }
    public void setSignagePhotoGeo(String signagePhotoGeo) { this.signagePhotoGeo = signagePhotoGeo; }

    public String getSignagePhotoTime() { return signagePhotoTime; }
    public void setSignagePhotoTime(String signagePhotoTime) { this.signagePhotoTime = signagePhotoTime; }

    public String getDeclarationFileGeo() { return declarationFileGeo; }
    public void setDeclarationFileGeo(String declarationFileGeo) { this.declarationFileGeo = declarationFileGeo; }

    public String getDeclarationFileTime() { return declarationFileTime; }
    public void setDeclarationFileTime(String declarationFileTime) { this.declarationFileTime = declarationFileTime; }

    public String getSurveyorName() { return surveyorName; }
    public void setSurveyorName(String surveyorName) { this.surveyorName = surveyorName; }

    public String getSurveyorSelfiePath() { return surveyorSelfiePath; }
    public void setSurveyorSelfiePath(String surveyorSelfiePath) { this.surveyorSelfiePath = surveyorSelfiePath; }

    public String getSurveyorGps() { return surveyorGps; }
    public void setSurveyorGps(String surveyorGps) { this.surveyorGps = surveyorGps; }

    public String getSurveyorTimestamp() { return surveyorTimestamp; }
    public void setSurveyorTimestamp(String surveyorTimestamp) { this.surveyorTimestamp = surveyorTimestamp; }

    public Boolean getSurveyorVerified() { return surveyorVerified; }
    public void setSurveyorVerified(Boolean surveyorVerified) { this.surveyorVerified = surveyorVerified; }

    public Boolean getCpcbCompleted() { return cpcbCompleted; }
    public void setCpcbCompleted(Boolean cpcbCompleted) { this.cpcbCompleted = cpcbCompleted; }

    public String getCpcbAckNumber() { return cpcbAckNumber; }
    public void setCpcbAckNumber(String cpcbAckNumber) { this.cpcbAckNumber = cpcbAckNumber; }

    public String getCpcbSubmissionDate() { return cpcbSubmissionDate; }
    public void setCpcbSubmissionDate(String cpcbSubmissionDate) { this.cpcbSubmissionDate = cpcbSubmissionDate; }

    public String getElectoralWardName() { return electoralWardName; }
    public void setElectoralWardName(String electoralWardName) { this.electoralWardName = electoralWardName; }

    public List<BwgSurveyWasteVisit> getWasteVisits() { return wasteVisits; }
    public void setWasteVisits(List<BwgSurveyWasteVisit> wasteVisits) { this.wasteVisits = wasteVisits; }
}
