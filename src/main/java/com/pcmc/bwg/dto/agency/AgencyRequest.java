package com.pcmc.bwg.dto.agency;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AgencyRequest {

    @NotBlank(message = "agencyName is required")
    @Size(max = 150)
    private String agencyName;

    @NotBlank(message = "agencyCode is required")
    @Size(max = 50)
    private String agencyCode;

    @NotBlank(message = "contactPersonName is required")
    @Size(max = 150)
    private String contactPersonName;

    @NotBlank(message = "mobileNo is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "mobileNo must be a valid 10-digit Indian mobile number")
    private String mobileNo;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "address is required")
    @Size(max = 500)
    private String address;

    @NotBlank(message = "pinCode is required")
    @Pattern(regexp = "^\\d{6}$", message = "pinCode must be a 6-digit code")
    private String pinCode;

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
