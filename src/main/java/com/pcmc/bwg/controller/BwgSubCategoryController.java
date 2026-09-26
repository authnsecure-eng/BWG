package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.BwgSubCategory;
import com.pcmc.bwg.service.AbstractNamedChildMasterService;
import com.pcmc.bwg.service.BwgSubCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/bwg-sub-categories")
public class BwgSubCategoryController extends AbstractNamedChildMasterController<BwgSubCategory> {

    private final BwgSubCategoryService bwgSubCategoryService;

    public BwgSubCategoryController(BwgSubCategoryService bwgSubCategoryService) {
        this.bwgSubCategoryService = bwgSubCategoryService;
    }

    @Override
    protected AbstractNamedChildMasterService<BwgSubCategory> service() {
        return bwgSubCategoryService;
    }
}
