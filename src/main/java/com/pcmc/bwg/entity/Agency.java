package com.pcmc.bwg.entity;

import com.pcmc.bwg.entity.enums.AgencyStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "agencies")
public class Agency extends BaseEntity {

    @Column(name = "agency_name", nullable = false, unique = true, length = 150)
    private String agencyName;

    @Column(name = "agency_code", nullable = false, unique = true, length = 50)
    private String agencyCode;

    @Column(name = "contact_person_name", nullable = false, length = 150)
    private String contactPersonName;

    @Column(name = "mobile_no", nullable = false, length = 15)
    private String mobileNo;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "address", nullable = false, length = 500)
    private String address;

    @Column(name = "pin_code", nullable = false, length = 10)
    private String pinCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AgencyStatus status = AgencyStatus.ACTIVE;

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

    public AgencyStatus getStatus() {
        return status;
    }

    public void setStatus(AgencyStatus status) {
        this.status = status;
    }
}
