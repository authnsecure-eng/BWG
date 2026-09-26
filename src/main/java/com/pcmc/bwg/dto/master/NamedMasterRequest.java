package com.pcmc.bwg.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NamedMasterRequest {

    @NotBlank(message = "name is required")
    @Size(max = 150, message = "name must be at most 150 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
