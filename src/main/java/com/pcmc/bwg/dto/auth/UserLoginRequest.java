package com.pcmc.bwg.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class UserLoginRequest {

    @NotBlank(message = "mobileNo is required")
    private String mobileNo;

    @NotBlank(message = "password is required")
    private String password;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
