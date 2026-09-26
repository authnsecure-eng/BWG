package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.Zone;
import com.pcmc.bwg.service.AbstractNamedMasterService;
import com.pcmc.bwg.service.ZoneService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/zones")
public class ZoneController extends AbstractNamedMasterController<Zone> {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @Override
    protected AbstractNamedMasterService<Zone> service() {
        return zoneService;
    }
}
