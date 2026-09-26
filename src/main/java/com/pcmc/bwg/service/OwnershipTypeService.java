package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.OwnershipType;
import com.pcmc.bwg.repository.OwnershipTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class OwnershipTypeService extends AbstractNamedMasterService<OwnershipType> {

    public OwnershipTypeService(OwnershipTypeRepository repository) {
        super(repository);
    }

    @Override
    protected OwnershipType newEntity() {
        return new OwnershipType();
    }

    @Override
    protected String label() {
        return "Ownership Type";
    }
}
