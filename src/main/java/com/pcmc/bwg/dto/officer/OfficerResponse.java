package com.pcmc.bwg.dto.officer;

import com.pcmc.bwg.entity.Officer;
import com.pcmc.bwg.entity.enums.MasterStatus;

import java.time.LocalDateTime;

public class OfficerResponse {

    private Long id;
    private String fullName;
    private String mobileNo;
    private String email;
    private Long departmentId;
    private Long designationId;
    private Long zoneId;
    private Long administrativeWardId;
    private MasterStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OfficerResponse from(Officer officer) {
        OfficerResponse dto = new OfficerResponse();
        dto.id = officer.getId();
        dto.fullName = officer.getFullName();
        dto.mobileNo = officer.getMobileNo();
        dto.email = officer.getEmail();
        dto.departmentId = officer.getDepartmentId();
        dto.designationId = officer.getDesignationId();
        dto.zoneId = officer.getZoneId();
        dto.administrativeWardId = officer.getAdministrativeWardId();
        dto.status = officer.getStatus();
        dto.createdAt = officer.getCreatedAt();
        dto.updatedAt = officer.getUpdatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public Long getAdministrativeWardId() {
        return administrativeWardId;
    }

    public MasterStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
