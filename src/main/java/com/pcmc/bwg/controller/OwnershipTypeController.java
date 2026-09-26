package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.OwnershipType;
import com.pcmc.bwg.service.AbstractNamedMasterService;
import com.pcmc.bwg.service.OwnershipTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/ownership-types")
public class OwnershipTypeController extends AbstractNamedMasterController<OwnershipType> {

    private final OwnershipTypeService ownershipTypeService;

    public OwnershipTypeController(OwnershipTypeService ownershipTypeService) {
        this.ownershipTypeService = ownershipTypeService;
    }

    @Override
    protected AbstractNamedMasterService<OwnershipType> service() {
        return ownershipTypeService;
    }
}
