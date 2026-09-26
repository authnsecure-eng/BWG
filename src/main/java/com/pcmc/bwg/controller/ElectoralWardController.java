package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.ElectoralWard;
import com.pcmc.bwg.service.AbstractNamedChildMasterService;
import com.pcmc.bwg.service.ElectoralWardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/electoral-wards")
public class ElectoralWardController extends AbstractNamedChildMasterController<ElectoralWard> {

    private final ElectoralWardService electoralWardService;

    public ElectoralWardController(ElectoralWardService electoralWardService) {
        this.electoralWardService = electoralWardService;
    }

    @Override
    protected AbstractNamedChildMasterService<ElectoralWard> service() {
        return electoralWardService;
    }
}
