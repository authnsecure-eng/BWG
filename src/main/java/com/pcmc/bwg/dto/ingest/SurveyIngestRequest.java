package com.pcmc.bwg.dto.ingest;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

/**
 * Payload the mobile app backend ("Bwg mobile" / BWG-Backend "mobile"
 * branch) pushes to this admin backend every time a survey is created or
 * updated on the mobile side (AdminBridgeClient). Field names deliberately
 * mirror the mobile app's own SurveyCreateRequest so the mapping in
 * SurveyIngestService stays a straight line to read.
 */
public class SurveyIngestRequest {

    @NotBlank(message = "mobileSurveyId is required")
    private String mobileSurveyId;

    private String category;
    private String establishmentName;
    private String zone;
    private String ward;
    private String electoralWard;
    private String contactName;
    private String contactDesignation;
    private String contactMobile;
    private String contactEmail;
    private String contactAddress;
    private String contactPincode;
    private String yearEstablished;
    private String premisesPhotoUrl;
    private String premisesPhotoGeo;
    private String premisesPhotoTime;
    private String signagePhotoUrl;
    private String signagePhotoGeo;
    private String signagePhotoTime;
    private String gpsCoordinates;
    private String subCategoryType;
    private String societyName;
    private String chsRegNo;
    private String orgName;
    private String cinNumber;
    private String tradeLicenseNo;
    private String ptin;
    private String gstin;
    private String totalFloors;
    private String totalUnits;
    private BigDecimal builtUpAreaSqM;
    private String buildingRemarks;
    private String buildingPermissionRefNo;
    private String buildingPermissionDocUrl;
    private String buildingPermissionGeo;
    private String buildingPermissionTime;
    private String waterConsumerNo;
    private BigDecimal dailyWaterConsumptionLiters;
    private String waterBillingPeriod;
    private String waterUnitsConsumed;
    private String waterBillDocUrl;
    private String binInfrastructure;
    private String segregatedAtSource;
    private String dryWasteChannelizedTo;
    private String overallDisposalMode;
    private String vendorName;
    private String mouValidity;
    private String processingDestination;
    private String privateVendorDetails;
    private Boolean hasBiogasPlant;
    private String processingMethod;
    private String biogasCapacity;
    private String biogasCapacityUnit;
    private String spaceAvailableSqMeters;
    private String byProductUsage;
    private String biogasOperationalStatus;
    private String biogasPhotoUrl;
    private String biogasRemarks;
    private String wasteGivenToOtherAgency;
    private String agencyDocumentPhotoUrl;
    private String geofenceLatitude;
    private String geofenceLongitude;
    private String geofenceRadiusMeters;
    private String geofencePhotoUrl;
    private Boolean eligibilityFloorArea;
    private Boolean eligibilityWaterConsumption;
    private Boolean eligibilitySolidWaste;
    private String declarantName;
    private String declarantDesignation;
    private String declarantOrgName;
    private String declarantDate;
    private String declarantPlace;
    private String declarationFileUrl;
    private String declarationFileGeo;
    private String declarationFileTime;
    private String surveyorName;
    private String surveyorSelfieUrl;
    private String surveyorGps;
    private String surveyorTimestamp;
    private Boolean surveyorVerified;
    private Boolean cpcbCompleted;
    private String cpcbAckNumber;
    private String cpcbSubmissionDate;

    // Set by the mobile backend from the logged-in officer's JWT, not the survey form.
    private Long surveyOfficerId;
    private String surveyOfficerName;

    private List<WasteVisitIngestDto> wasteVisits;

    public String getMobileSurveyId() { return mobileSurveyId; }
    public void setMobileSurveyId(String mobileSurveyId) { this.mobileSurveyId = mobileSurveyId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getEstablishmentName() { return establishmentName; }
    public void setEstablishmentName(String establishmentName) { this.establishmentName = establishmentName; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }

    public String getElectoralWard() { return electoralWard; }
    public void setElectoralWard(String electoralWard) { this.electoralWard = electoralWard; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getContactDesignation() { return contactDesignation; }
    public void setContactDesignation(String contactDesignation) { this.contactDesignation = contactDesignation; }

    public String getContactMobile() { return contactMobile; }
    public void setContactMobile(String contactMobile) { this.contactMobile = contactMobile; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getContactAddress() { return contactAddress; }
    public void setContactAddress(String contactAddress) { this.contactAddress = contactAddress; }

    public String getContactPincode() { return contactPincode; }
    public void setContactPincode(String contactPincode) { this.contactPincode = contactPincode; }

    public String getYearEstablished() { return yearEstablished; }
    public void setYearEstablished(String yearEstablished) { this.yearEstablished = yearEstablished; }

    public String getPremisesPhotoUrl() { return premisesPhotoUrl; }
    public void setPremisesPhotoUrl(String premisesPhotoUrl) { this.premisesPhotoUrl = premisesPhotoUrl; }

    public String getPremisesPhotoGeo() { return premisesPhotoGeo; }
    public void setPremisesPhotoGeo(String premisesPhotoGeo) { this.premisesPhotoGeo = premisesPhotoGeo; }

    public String getPremisesPhotoTime() { return premisesPhotoTime; }
    public void setPremisesPhotoTime(String premisesPhotoTime) { this.premisesPhotoTime = premisesPhotoTime; }

    public String getSignagePhotoUrl() { return signagePhotoUrl; }
    public void setSignagePhotoUrl(String signagePhotoUrl) { this.signagePhotoUrl = signagePhotoUrl; }

    public String getSignagePhotoGeo() { return signagePhotoGeo; }
    public void setSignagePhotoGeo(String signagePhotoGeo) { this.signagePhotoGeo = signagePhotoGeo; }

    public String getSignagePhotoTime() { return signagePhotoTime; }
    public void setSignagePhotoTime(String signagePhotoTime) { this.signagePhotoTime = signagePhotoTime; }

    public String getGpsCoordinates() { return gpsCoordinates; }
    public void setGpsCoordinates(String gpsCoordinates) { this.gpsCoordinates = gpsCoordinates; }

    public String getSubCategoryType() { return subCategoryType; }
    public void setSubCategoryType(String subCategoryType) { this.subCategoryType = subCategoryType; }

    public String getSocietyName() { return societyName; }
    public void setSocietyName(String societyName) { this.societyName = societyName; }

    public String getChsRegNo() { return chsRegNo; }
    public void setChsRegNo(String chsRegNo) { this.chsRegNo = chsRegNo; }

    public String getOrgName() { return orgName; }
    public void setOrgName(String orgName) { this.orgName = orgName; }

    public String getCinNumber() { return cinNumber; }
    public void setCinNumber(String cinNumber) { this.cinNumber = cinNumber; }

    public String getTradeLicenseNo() { return tradeLicenseNo; }
    public void setTradeLicenseNo(String tradeLicenseNo) { this.tradeLicenseNo = tradeLicenseNo; }

    public String getPtin() { return ptin; }
    public void setPtin(String ptin) { this.ptin = ptin; }

    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }

    public String getTotalFloors() { return totalFloors; }
    public void setTotalFloors(String totalFloors) { this.totalFloors = totalFloors; }

    public String getTotalUnits() { return totalUnits; }
    public void setTotalUnits(String totalUnits) { this.totalUnits = totalUnits; }

    public BigDecimal getBuiltUpAreaSqM() { return builtUpAreaSqM; }
    public void setBuiltUpAreaSqM(BigDecimal builtUpAreaSqM) { this.builtUpAreaSqM = builtUpAreaSqM; }

    public String getBuildingRemarks() { return buildingRemarks; }
    public void setBuildingRemarks(String buildingRemarks) { this.buildingRemarks = buildingRemarks; }

    public String getBuildingPermissionRefNo() { return buildingPermissionRefNo; }
    public void setBuildingPermissionRefNo(String buildingPermissionRefNo) { this.buildingPermissionRefNo = buildingPermissionRefNo; }

    public String getBuildingPermissionDocUrl() { return buildingPermissionDocUrl; }
    public void setBuildingPermissionDocUrl(String buildingPermissionDocUrl) { this.buildingPermissionDocUrl = buildingPermissionDocUrl; }

    public String getBuildingPermissionGeo() { return buildingPermissionGeo; }
    public void setBuildingPermissionGeo(String buildingPermissionGeo) { this.buildingPermissionGeo = buildingPermissionGeo; }

    public String getBuildingPermissionTime() { return buildingPermissionTime; }
    public void setBuildingPermissionTime(String buildingPermissionTime) { this.buildingPermissionTime = buildingPermissionTime; }

    public String getWaterConsumerNo() { return waterConsumerNo; }
    public void setWaterConsumerNo(String waterConsumerNo) { this.waterConsumerNo = waterConsumerNo; }

    public BigDecimal getDailyWaterConsumptionLiters() { return dailyWaterConsumptionLiters; }
    public void setDailyWaterConsumptionLiters(BigDecimal dailyWaterConsumptionLiters) { this.dailyWaterConsumptionLiters = dailyWaterConsumptionLiters; }

    public String getWaterBillingPeriod() { return waterBillingPeriod; }
    public void setWaterBillingPeriod(String waterBillingPeriod) { this.waterBillingPeriod = waterBillingPeriod; }

    public String getWaterUnitsConsumed() { return waterUnitsConsumed; }
    public void setWaterUnitsConsumed(String waterUnitsConsumed) { this.waterUnitsConsumed = waterUnitsConsumed; }

    public String getWaterBillDocUrl() { return waterBillDocUrl; }
    public void setWaterBillDocUrl(String waterBillDocUrl) { this.waterBillDocUrl = waterBillDocUrl; }

    public String getBinInfrastructure() { return binInfrastructure; }
    public void setBinInfrastructure(String binInfrastructure) { this.binInfrastructure = binInfrastructure; }

    public String getSegregatedAtSource() { return segregatedAtSource; }
    public void setSegregatedAtSource(String segregatedAtSource) { this.segregatedAtSource = segregatedAtSource; }

    public String getDryWasteChannelizedTo() { return dryWasteChannelizedTo; }
    public void setDryWasteChannelizedTo(String dryWasteChannelizedTo) { this.dryWasteChannelizedTo = dryWasteChannelizedTo; }

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

    public Boolean getHasBiogasPlant() { return hasBiogasPlant; }
    public void setHasBiogasPlant(Boolean hasBiogasPlant) { this.hasBiogasPlant = hasBiogasPlant; }

    public String getProcessingMethod() { return processingMethod; }
    public void setProcessingMethod(String processingMethod) { this.processingMethod = processingMethod; }

    public String getBiogasCapacity() { return biogasCapacity; }
    public void setBiogasCapacity(String biogasCapacity) { this.biogasCapacity = biogasCapacity; }

    public String getBiogasCapacityUnit() { return biogasCapacityUnit; }
    public void setBiogasCapacityUnit(String biogasCapacityUnit) { this.biogasCapacityUnit = biogasCapacityUnit; }

    public String getSpaceAvailableSqMeters() { return spaceAvailableSqMeters; }
    public void setSpaceAvailableSqMeters(String spaceAvailableSqMeters) { this.spaceAvailableSqMeters = spaceAvailableSqMeters; }

    public String getByProductUsage() { return byProductUsage; }
    public void setByProductUsage(String byProductUsage) { this.byProductUsage = byProductUsage; }

    public String getBiogasOperationalStatus() { return biogasOperationalStatus; }
    public void setBiogasOperationalStatus(String biogasOperationalStatus) { this.biogasOperationalStatus = biogasOperationalStatus; }

    public String getBiogasPhotoUrl() { return biogasPhotoUrl; }
    public void setBiogasPhotoUrl(String biogasPhotoUrl) { this.biogasPhotoUrl = biogasPhotoUrl; }

    public String getBiogasRemarks() { return biogasRemarks; }
    public void setBiogasRemarks(String biogasRemarks) { this.biogasRemarks = biogasRemarks; }

    public String getWasteGivenToOtherAgency() { return wasteGivenToOtherAgency; }
    public void setWasteGivenToOtherAgency(String wasteGivenToOtherAgency) { this.wasteGivenToOtherAgency = wasteGivenToOtherAgency; }

    public String getAgencyDocumentPhotoUrl() { return agencyDocumentPhotoUrl; }
    public void setAgencyDocumentPhotoUrl(String agencyDocumentPhotoUrl) { this.agencyDocumentPhotoUrl = agencyDocumentPhotoUrl; }

    public String getGeofenceLatitude() { return geofenceLatitude; }
    public void setGeofenceLatitude(String geofenceLatitude) { this.geofenceLatitude = geofenceLatitude; }

    public String getGeofenceLongitude() { return geofenceLongitude; }
    public void setGeofenceLongitude(String geofenceLongitude) { this.geofenceLongitude = geofenceLongitude; }

    public String getGeofenceRadiusMeters() { return geofenceRadiusMeters; }
    public void setGeofenceRadiusMeters(String geofenceRadiusMeters) { this.geofenceRadiusMeters = geofenceRadiusMeters; }

    public String getGeofencePhotoUrl() { return geofencePhotoUrl; }
    public void setGeofencePhotoUrl(String geofencePhotoUrl) { this.geofencePhotoUrl = geofencePhotoUrl; }

    public Boolean getEligibilityFloorArea() { return eligibilityFloorArea; }
    public void setEligibilityFloorArea(Boolean eligibilityFloorArea) { this.eligibilityFloorArea = eligibilityFloorArea; }

    public Boolean getEligibilityWaterConsumption() { return eligibilityWaterConsumption; }
    public void setEligibilityWaterConsumption(Boolean eligibilityWaterConsumption) { this.eligibilityWaterConsumption = eligibilityWaterConsumption; }

    public Boolean getEligibilitySolidWaste() { return eligibilitySolidWaste; }
    public void setEligibilitySolidWaste(Boolean eligibilitySolidWaste) { this.eligibilitySolidWaste = eligibilitySolidWaste; }

    public String getDeclarantName() { return declarantName; }
    public void setDeclarantName(String declarantName) { this.declarantName = declarantName; }

    public String getDeclarantDesignation() { return declarantDesignation; }
    public void setDeclarantDesignation(String declarantDesignation) { this.declarantDesignation = declarantDesignation; }

    public String getDeclarantOrgName() { return declarantOrgName; }
    public void setDeclarantOrgName(String declarantOrgName) { this.declarantOrgName = declarantOrgName; }

    public String getDeclarantDate() { return declarantDate; }
    public void setDeclarantDate(String declarantDate) { this.declarantDate = declarantDate; }

    public String getDeclarantPlace() { return declarantPlace; }
    public void setDeclarantPlace(String declarantPlace) { this.declarantPlace = declarantPlace; }

    public String getDeclarationFileUrl() { return declarationFileUrl; }
    public void setDeclarationFileUrl(String declarationFileUrl) { this.declarationFileUrl = declarationFileUrl; }

    public String getDeclarationFileGeo() { return declarationFileGeo; }
    public void setDeclarationFileGeo(String declarationFileGeo) { this.declarationFileGeo = declarationFileGeo; }

    public String getDeclarationFileTime() { return declarationFileTime; }
    public void setDeclarationFileTime(String declarationFileTime) { this.declarationFileTime = declarationFileTime; }

    public String getSurveyorName() { return surveyorName; }
    public void setSurveyorName(String surveyorName) { this.surveyorName = surveyorName; }

    public String getSurveyorSelfieUrl() { return surveyorSelfieUrl; }
    public void setSurveyorSelfieUrl(String surveyorSelfieUrl) { this.surveyorSelfieUrl = surveyorSelfieUrl; }

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

    public Long getSurveyOfficerId() { return surveyOfficerId; }
    public void setSurveyOfficerId(Long surveyOfficerId) { this.surveyOfficerId = surveyOfficerId; }

    public String getSurveyOfficerName() { return surveyOfficerName; }
    public void setSurveyOfficerName(String surveyOfficerName) { this.surveyOfficerName = surveyOfficerName; }

    public List<WasteVisitIngestDto> getWasteVisits() { return wasteVisits; }
    public void setWasteVisits(List<WasteVisitIngestDto> wasteVisits) { this.wasteVisits = wasteVisits; }
}
