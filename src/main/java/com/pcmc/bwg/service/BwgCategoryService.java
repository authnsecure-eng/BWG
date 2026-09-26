package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.BwgCategory;
import com.pcmc.bwg.repository.BwgCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class BwgCategoryService extends AbstractNamedMasterService<BwgCategory> {

    public BwgCategoryService(BwgCategoryRepository repository) {
        super(repository);
    }

    @Override
    protected BwgCategory newEntity() {
        return new BwgCategory();
    }

    @Override
    protected String label() {
        return "BWG Category";
    }
}
