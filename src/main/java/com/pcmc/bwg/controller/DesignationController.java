package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.Designation;
import com.pcmc.bwg.service.AbstractNamedMasterService;
import com.pcmc.bwg.service.DesignationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/designations")
public class DesignationController extends AbstractNamedMasterController<Designation> {

    private final DesignationService designationService;

    public DesignationController(DesignationService designationService) {
        this.designationService = designationService;
    }

    @Override
    protected AbstractNamedMasterService<Designation> service() {
        return designationService;
    }
}
