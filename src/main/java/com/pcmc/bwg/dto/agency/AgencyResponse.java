package com.pcmc.bwg.dto.agency;

import com.pcmc.bwg.entity.Agency;
import com.pcmc.bwg.entity.enums.AgencyStatus;

import java.time.LocalDateTime;

public class AgencyResponse {

    private Long id;
    private String agencyName;
    private String agencyCode;
    private String contactPersonName;
    private String mobileNo;
    private String email;
    private String address;
    private String pinCode;
    private AgencyStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AgencyResponse from(Agency agency) {
        AgencyResponse dto = new AgencyResponse();
        dto.id = agency.getId();
        dto.agencyName = agency.getAgencyName();
        dto.agencyCode = agency.getAgencyCode();
        dto.contactPersonName = agency.getContactPersonName();
        dto.mobileNo = agency.getMobileNo();
        dto.email = agency.getEmail();
        dto.address = agency.getAddress();
        dto.pinCode = agency.getPinCode();
        dto.status = agency.getStatus();
        dto.createdAt = agency.getCreatedAt();
        dto.updatedAt = agency.getUpdatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public AgencyStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
