package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.AdministrativeWard;
import com.pcmc.bwg.service.AbstractNamedChildMasterService;
import com.pcmc.bwg.service.AdministrativeWardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/administrative-wards")
public class AdministrativeWardController extends AbstractNamedChildMasterController<AdministrativeWard> {

    private final AdministrativeWardService administrativeWardService;

    public AdministrativeWardController(AdministrativeWardService administrativeWardService) {
        this.administrativeWardService = administrativeWardService;
    }

    @Override
    protected AbstractNamedChildMasterService<AdministrativeWard> service() {
        return administrativeWardService;
    }
}
