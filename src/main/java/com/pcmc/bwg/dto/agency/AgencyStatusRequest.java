package com.pcmc.bwg.dto.agency;

import com.pcmc.bwg.entity.enums.AgencyStatus;
import jakarta.validation.constraints.NotNull;

public class AgencyStatusRequest {

    @NotNull(message = "status is required")
    private AgencyStatus status;

    public AgencyStatus getStatus() {
        return status;
    }

    public void setStatus(AgencyStatus status) {
        this.status = status;
    }
}
