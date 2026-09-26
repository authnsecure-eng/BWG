package com.pcmc.bwg.dto.master;

import com.pcmc.bwg.entity.enums.MasterStatus;
import jakarta.validation.constraints.NotNull;

public class MasterStatusRequest {

    @NotNull(message = "status is required")
    private MasterStatus status;

    public MasterStatus getStatus() {
        return status;
    }

    public void setStatus(MasterStatus status) {
        this.status = status;
    }
}
