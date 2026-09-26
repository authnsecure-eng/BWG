package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.ProcessingInfrastructureType;
import com.pcmc.bwg.service.AbstractNamedMasterService;
import com.pcmc.bwg.service.ProcessingInfrastructureTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/processing-infrastructure-types")
public class ProcessingInfrastructureTypeController
        extends AbstractNamedMasterController<ProcessingInfrastructureType> {

    private final ProcessingInfrastructureTypeService processingInfrastructureTypeService;

    public ProcessingInfrastructureTypeController(
            ProcessingInfrastructureTypeService processingInfrastructureTypeService) {
        this.processingInfrastructureTypeService = processingInfrastructureTypeService;
    }

    @Override
    protected AbstractNamedMasterService<ProcessingInfrastructureType> service() {
        return processingInfrastructureTypeService;
    }
}
