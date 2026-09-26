package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.BwgSubCategory;
import com.pcmc.bwg.repository.BwgCategoryRepository;
import com.pcmc.bwg.repository.BwgSubCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class BwgSubCategoryService extends AbstractNamedChildMasterService<BwgSubCategory> {

    public BwgSubCategoryService(BwgSubCategoryRepository repository, BwgCategoryRepository categoryRepository) {
        super(repository, repository::findByParentId, categoryRepository::existsById, "BWG Category");
    }

    @Override
    protected BwgSubCategory newEntity() {
        return new BwgSubCategory();
    }

    @Override
    protected String label() {
        return "BWG Sub-Category";
    }
}
