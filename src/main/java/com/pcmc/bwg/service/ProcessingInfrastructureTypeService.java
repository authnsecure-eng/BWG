package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.ProcessingInfrastructureType;
import com.pcmc.bwg.repository.ProcessingInfrastructureTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class ProcessingInfrastructureTypeService extends AbstractNamedMasterService<ProcessingInfrastructureType> {

    public ProcessingInfrastructureTypeService(ProcessingInfrastructureTypeRepository repository) {
        super(repository);
    }

    @Override
    protected ProcessingInfrastructureType newEntity() {
        return new ProcessingInfrastructureType();
    }

    @Override
    protected String label() {
        return "Processing Infrastructure Type";
    }
}
