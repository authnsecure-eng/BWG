package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.BwgCategory;
import com.pcmc.bwg.service.AbstractNamedMasterService;
import com.pcmc.bwg.service.BwgCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/bwg-categories")
public class BwgCategoryController extends AbstractNamedMasterController<BwgCategory> {

    private final BwgCategoryService bwgCategoryService;

    public BwgCategoryController(BwgCategoryService bwgCategoryService) {
        this.bwgCategoryService = bwgCategoryService;
    }

    @Override
    protected AbstractNamedMasterService<BwgCategory> service() {
        return bwgCategoryService;
    }
}
