package com.pcmc.bwg.dto.user;

import com.pcmc.bwg.entity.AppUser;
import com.pcmc.bwg.entity.enums.Role;
import com.pcmc.bwg.entity.enums.UserStatus;

import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private Long agencyId;
    private String agencyName;
    private String fullName;
    private String mobileNo;
    private String email;
    private String aadhaarNo;
    private String address;
    private String pinCode;
    private String photoPath;
    private Role role;
    private UserStatus status;
    private boolean mobileVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponse from(AppUser user, String maskedAadhaarNo) {
        UserResponse dto = new UserResponse();
        dto.id = user.getId();
        dto.agencyId = user.getAgency().getId();
        dto.agencyName = user.getAgency().getAgencyName();
        dto.fullName = user.getFullName();
        dto.mobileNo = user.getMobileNo();
        dto.email = user.getEmail();
        dto.aadhaarNo = maskedAadhaarNo;
        dto.address = user.getAddress();
        dto.pinCode = user.getPinCode();
        dto.photoPath = user.getPhotoPath();
        dto.role = user.getRole();
        dto.status = user.getStatus();
        dto.mobileVerified = user.isMobileVerified();
        dto.createdAt = user.getCreatedAt();
        dto.updatedAt = user.getUpdatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public String getAgencyName() {
        return agencyName;
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

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public String getAddress() {
        return address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Role getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
