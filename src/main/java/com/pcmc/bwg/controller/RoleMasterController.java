package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.RoleMaster;
import com.pcmc.bwg.service.AbstractNamedMasterService;
import com.pcmc.bwg.service.RoleMasterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/roles")
public class RoleMasterController extends AbstractNamedMasterController<RoleMaster> {

    private final RoleMasterService roleMasterService;

    public RoleMasterController(RoleMasterService roleMasterService) {
        this.roleMasterService = roleMasterService;
    }

    @Override
    protected AbstractNamedMasterService<RoleMaster> service() {
        return roleMasterService;
    }
}
