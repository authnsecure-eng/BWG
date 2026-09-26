package com.pcmc.bwg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "electoral_ward_user_mappings")
public class ElectoralWardUserMapping extends BaseEntity {

    @Column(name = "electoral_ward_id", nullable = false)
    private Long electoralWardId;

    @Column(name = "app_user_id", nullable = false)
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
