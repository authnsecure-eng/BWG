package com.pcmc.bwg.dto.officer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OfficerRequest {

    @NotBlank(message = "fullName is required")
    @Size(max = 150)
    private String fullName;

    @NotBlank(message = "mobileNo is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "mobileNo must be a valid 10-digit Indian mobile number")
    private String mobileNo;

    @Email(message = "email must be valid")
    private String email;

    @NotNull(message = "departmentId is required")
    private Long departmentId;

    @NotNull(message = "designationId is required")
    private Long designationId;

    private Long zoneId;

    private Long administrativeWardId;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getAdministrativeWardId() {
        return administrativeWardId;
    }

    public void setAdministrativeWardId(Long administrativeWardId) {
        this.administrativeWardId = administrativeWardId;
    }
}
