package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.WasteType;
import com.pcmc.bwg.service.AbstractNamedMasterService;
import com.pcmc.bwg.service.WasteTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/waste-types")
public class WasteTypeController extends AbstractNamedMasterController<WasteType> {

    private final WasteTypeService wasteTypeService;

    public WasteTypeController(WasteTypeService wasteTypeService) {
        this.wasteTypeService = wasteTypeService;
    }

    @Override
    protected AbstractNamedMasterService<WasteType> service() {
        return wasteTypeService;
    }
}
