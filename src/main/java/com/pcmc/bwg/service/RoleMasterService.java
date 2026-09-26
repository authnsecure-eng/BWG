package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.RoleMaster;
import com.pcmc.bwg.repository.RoleMasterRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleMasterService extends AbstractNamedMasterService<RoleMaster> {

    public RoleMasterService(RoleMasterRepository repository) {
        super(repository);
    }

    @Override
    protected RoleMaster newEntity() {
        return new RoleMaster();
    }

    @Override
    protected String label() {
        return "Role";
    }
}
