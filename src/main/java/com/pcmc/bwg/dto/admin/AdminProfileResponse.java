package com.pcmc.bwg.dto.admin;

import com.pcmc.bwg.entity.Admin;

public class AdminProfileResponse {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String mobileNo;
    private String photoPath;
    private String role = "ADMIN";

    public static AdminProfileResponse from(Admin admin) {
        AdminProfileResponse dto = new AdminProfileResponse();
        dto.id = admin.getId();
        dto.username = admin.getUsername();
        dto.fullName = admin.getFullName();
        dto.email = admin.getEmail();
        dto.mobileNo = admin.getMobileNo();
        dto.photoPath = admin.getPhotoPath();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getRole() {
        return role;
    }
}
