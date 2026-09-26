package com.pcmc.bwg.dto.master;

import com.pcmc.bwg.entity.NamedChildMasterEntity;
import com.pcmc.bwg.entity.enums.MasterStatus;

import java.time.LocalDateTime;

public class NamedChildMasterResponse {

    private Long id;
    private Long parentId;
    private String name;
    private MasterStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NamedChildMasterResponse from(NamedChildMasterEntity entity) {
        NamedChildMasterResponse dto = new NamedChildMasterResponse();
        dto.id = entity.getId();
        dto.parentId = entity.getParentId();
        dto.name = entity.getName();
        dto.status = entity.getStatus();
        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
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
