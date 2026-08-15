package com.pcmc.bwg.dto.otp;

public class OtpResponse {

    private String message;
    private int expiryMinutes;
    private String devOtp;

    public OtpResponse(String message, int expiryMinutes, String devOtp) {
        this.message = message;
        this.expiryMinutes = expiryMinutes;
        this.devOtp = devOtp;
    }

    public String getMessage() {
        return message;
    }

    public int getExpiryMinutes() {
        return expiryMinutes;
    }

    public String getDevOtp() {
        return devOtp;
    }
}
