package com.pcmc.bwg.entity;

import com.pcmc.bwg.entity.enums.MasterStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

/**
 * Shared shape for simple lookup masters that are just a unique name with a
 * status toggle (Zone, Department, Designation, BWG Category, ...). Concrete
 * subclasses only need an @Entity/@Table annotation.
 */
@MappedSuperclass
public abstract class NamedMasterEntity extends BaseEntity {

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MasterStatus status = MasterStatus.ACTIVE;

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
