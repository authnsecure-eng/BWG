package com.pcmc.bwg.dto.mapping;

import jakarta.validation.constraints.NotNull;

public class ElectoralWardUserMappingRequest {

    @NotNull(message = "electoralWardId is required")
    private Long electoralWardId;

    @NotNull(message = "appUserId is required")
    private Long appUserId;

    public Long getElectoralWardId() {
        return electoralWardId;
    }

    public void setElectoralWardId(Long electoralWardId) {
        this.electoralWardId = electoralWardId;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }
}
