package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.ElectoralWard;
import com.pcmc.bwg.repository.AdministrativeWardRepository;
import com.pcmc.bwg.repository.ElectoralWardRepository;
import org.springframework.stereotype.Service;

@Service
public class ElectoralWardService extends AbstractNamedChildMasterService<ElectoralWard> {

    public ElectoralWardService(ElectoralWardRepository repository, AdministrativeWardRepository wardRepository) {
        super(repository, repository::findByParentId, wardRepository::existsById, "Administrative Ward");
    }

    @Override
    protected ElectoralWard newEntity() {
        return new ElectoralWard();
    }

    @Override
    protected String label() {
        return "Electoral Ward";
    }
}
