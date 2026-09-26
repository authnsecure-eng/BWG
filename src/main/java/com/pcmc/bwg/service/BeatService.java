package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.Beat;
import com.pcmc.bwg.repository.BeatRepository;
import com.pcmc.bwg.repository.ElectoralWardRepository;
import org.springframework.stereotype.Service;

@Service
public class BeatService extends AbstractNamedChildMasterService<Beat> {

    public BeatService(BeatRepository repository, ElectoralWardRepository electoralWardRepository) {
        super(repository, repository::findByParentId, electoralWardRepository::existsById, "Electoral Ward");
    }

    @Override
    protected Beat newEntity() {
        return new Beat();
    }

    @Override
    protected String label() {
        return "Beat";
    }
}
