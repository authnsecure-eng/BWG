package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.Zone;
import com.pcmc.bwg.repository.ZoneRepository;
import org.springframework.stereotype.Service;

@Service
public class ZoneService extends AbstractNamedMasterService<Zone> {

    public ZoneService(ZoneRepository repository) {
        super(repository);
    }

    @Override
    protected Zone newEntity() {
        return new Zone();
    }

    @Override
    protected String label() {
        return "Zone";
    }
}
