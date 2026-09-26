package com.pcmc.bwg.dto.report;

import com.pcmc.bwg.entity.enums.SurveyStatus;

import java.time.OffsetDateTime;

public class AdminReportResponse {

    private Long id;
    private String applicationNo;
    private String bwgName;
    private String contactPerson;
    private String mobileNo;
    private String zone;
    private String ward;
    private String category;
    private SurveyStatus status;
    private OffsetDateTime submittedAt;

    public AdminReportResponse(Long id, String applicationNo, String bwgName, String contactPerson,
                                String mobileNo, String zone, String ward, String category,
                                SurveyStatus status, OffsetDateTime submittedAt) {
        this.id = id;
        this.applicationNo = applicationNo;
        this.bwgName = bwgName;
        this.contactPerson = contactPerson;
        this.mobileNo = mobileNo;
        this.zone = zone;
        this.ward = ward;
        this.category = category;
        this.status = status;
        this.submittedAt = submittedAt;
    }

    public Long getId() {
        return id;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public String getBwgName() {
        return bwgName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getZone() {
        return zone;
    }

    public String getWard() {
        return ward;
    }

    public String getCategory() {
        return category;
    }

    public SurveyStatus getStatus() {
        return status;
    }

    public OffsetDateTime getSubmittedAt() {
        return submittedAt;
    }
}
