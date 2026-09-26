package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.PropertyType;
import com.pcmc.bwg.repository.PropertyTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class PropertyTypeService extends AbstractNamedMasterService<PropertyType> {

    public PropertyTypeService(PropertyTypeRepository repository) {
        super(repository);
    }

    @Override
    protected PropertyType newEntity() {
        return new PropertyType();
    }

    @Override
    protected String label() {
        return "Property Type";
    }
}
