package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.UnitType;
import com.pcmc.bwg.service.AbstractNamedMasterService;
import com.pcmc.bwg.service.UnitTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/unit-types")
public class UnitTypeController extends AbstractNamedMasterController<UnitType> {

    private final UnitTypeService unitTypeService;

    public UnitTypeController(UnitTypeService unitTypeService) {
        this.unitTypeService = unitTypeService;
    }

    @Override
    protected AbstractNamedMasterService<UnitType> service() {
        return unitTypeService;
    }
}
