package com.pcmc.bwg.dto.mapping;

import com.pcmc.bwg.entity.ElectoralWardUserMapping;

import java.time.LocalDateTime;

public class ElectoralWardUserMappingResponse {

    private Long id;
    private Long electoralWardId;
    private Long appUserId;
    private LocalDateTime createdAt;

    public static ElectoralWardUserMappingResponse from(ElectoralWardUserMapping mapping) {
        ElectoralWardUserMappingResponse dto = new ElectoralWardUserMappingResponse();
        dto.id = mapping.getId();
        dto.electoralWardId = mapping.getElectoralWardId();
        dto.appUserId = mapping.getAppUserId();
        dto.createdAt = mapping.getCreatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public Long getElectoralWardId() {
        return electoralWardId;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
