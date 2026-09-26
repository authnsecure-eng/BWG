package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.AdministrativeWard;
import com.pcmc.bwg.repository.AdministrativeWardRepository;
import com.pcmc.bwg.repository.ZoneRepository;
import org.springframework.stereotype.Service;

@Service
public class AdministrativeWardService extends AbstractNamedChildMasterService<AdministrativeWard> {

    public AdministrativeWardService(AdministrativeWardRepository repository, ZoneRepository zoneRepository) {
        super(repository, repository::findByParentId, zoneRepository::existsById, "Zone");
    }

    @Override
    protected AdministrativeWard newEntity() {
        return new AdministrativeWard();
    }

    @Override
    protected String label() {
        return "Administrative Ward";
    }
}
