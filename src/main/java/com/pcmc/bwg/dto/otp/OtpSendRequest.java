package com.pcmc.bwg.dto.otp;

import jakarta.validation.constraints.NotBlank;

public class OtpSendRequest {

    @NotBlank(message = "mobileNo is required")
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
