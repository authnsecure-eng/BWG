package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.PropertyType;
import com.pcmc.bwg.service.AbstractNamedMasterService;
import com.pcmc.bwg.service.PropertyTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/property-types")
public class PropertyTypeController extends AbstractNamedMasterController<PropertyType> {

    private final PropertyTypeService propertyTypeService;

    public PropertyTypeController(PropertyTypeService propertyTypeService) {
        this.propertyTypeService = propertyTypeService;
    }

    @Override
    protected AbstractNamedMasterService<PropertyType> service() {
        return propertyTypeService;
    }
}
