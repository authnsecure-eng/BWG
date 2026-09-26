package com.pcmc.bwg.dto.report;

import jakarta.validation.constraints.NotBlank;

public class ReportStatusUpdateRequest {

    @NotBlank(message = "status is required")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
