package com.pcmc.bwg.dto.user;

import com.pcmc.bwg.entity.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

public class UserStatusRequest {

    @NotNull(message = "status is required")
    private UserStatus status;

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
