package com.pcmc.bwg.entity;

import com.pcmc.bwg.entity.enums.MasterStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

/**
 * Shared shape for cascading masters that belong to a parent master
 * (Administrative Ward -> Zone, Electoral Ward -> Administrative Ward, Beat
 * -> Electoral Ward, BWG Sub-Category -> BWG Category). parentId is a plain
 * FK column rather than a JPA association so this superclass stays generic
 * across all four chains; each concrete table enforces the real foreign key
 * (with ON DELETE CASCADE) at the database level via Flyway.
 */
@MappedSuperclass
public abstract class NamedChildMasterEntity extends BaseEntity {

    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MasterStatus status = MasterStatus.ACTIVE;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MasterStatus getStatus() {
        return status;
    }

    public void setStatus(MasterStatus status) {
        this.status = status;
    }
}
