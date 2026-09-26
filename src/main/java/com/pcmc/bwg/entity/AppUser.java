package com.pcmc.bwg.entity;

import com.pcmc.bwg.entity.enums.Role;
import com.pcmc.bwg.entity.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * The actual Survey Officer created by an Admin under an Agency.
 */
@Entity
@Table(name = "users")
public class AppUser extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "mobile_no", nullable = false, unique = true, length = 15)
    private String mobileNo;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "aadhaar_no_encrypted", nullable = false)
    private String aadhaarNoEncrypted;

    @Column(name = "aadhaar_no_hash", nullable = false, unique = true, length = 64)
    private String aadhaarNoHash;

    @Column(name = "address", nullable = false, length = 500)
    private String address;

    @Column(name = "pin_code", nullable = false, length = 10)
    private String pinCode;

    @Column(name = "photo_path", length = 500)
    private String photoPath;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private Role role = Role.SURVEY_OFFICER;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private UserStatus status = UserStatus.INACTIVE;

    @Column(name = "mobile_verified", nullable = false)
    private boolean mobileVerified = false;

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

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

    public String getAadhaarNoEncrypted() {
        return aadhaarNoEncrypted;
    }

    public void setAadhaarNoEncrypted(String aadhaarNoEncrypted) {
        this.aadhaarNoEncrypted = aadhaarNoEncrypted;
    }

    public String getAadhaarNoHash() {
        return aadhaarNoHash;
    }

    public void setAadhaarNoHash(String aadhaarNoHash) {
        this.aadhaarNoHash = aadhaarNoHash;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }
}
