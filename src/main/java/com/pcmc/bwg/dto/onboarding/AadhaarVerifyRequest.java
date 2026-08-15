package com.pcmc.bwg.dto.onboarding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AadhaarVerifyRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotBlank(message = "aadhaarNo is required")
    private String aadhaarNo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }
}
