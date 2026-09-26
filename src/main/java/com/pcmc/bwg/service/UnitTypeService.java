package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.UnitType;
import com.pcmc.bwg.repository.UnitTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class UnitTypeService extends AbstractNamedMasterService<UnitType> {

    public UnitTypeService(UnitTypeRepository repository) {
        super(repository);
    }

    @Override
    protected UnitType newEntity() {
        return new UnitType();
    }

    @Override
    protected String label() {
        return "Unit Type";
    }
}
