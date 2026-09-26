package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.WasteType;
import com.pcmc.bwg.repository.WasteTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class WasteTypeService extends AbstractNamedMasterService<WasteType> {

    public WasteTypeService(WasteTypeRepository repository) {
        super(repository);
    }

    @Override
    protected WasteType newEntity() {
        return new WasteType();
    }

    @Override
    protected String label() {
        return "Waste Type";
    }
}
