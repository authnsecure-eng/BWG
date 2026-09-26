package com.pcmc.bwg.dto.master;

import com.pcmc.bwg.entity.NamedMasterEntity;
import com.pcmc.bwg.entity.enums.MasterStatus;

import java.time.LocalDateTime;

public class NamedMasterResponse {

    private Long id;
    private String name;
    private MasterStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NamedMasterResponse from(NamedMasterEntity entity) {
        NamedMasterResponse dto = new NamedMasterResponse();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.status = entity.getStatus();
        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MasterStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
