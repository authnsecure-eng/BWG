package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.Designation;
import com.pcmc.bwg.repository.DesignationRepository;
import org.springframework.stereotype.Service;

@Service
public class DesignationService extends AbstractNamedMasterService<Designation> {

    public DesignationService(DesignationRepository repository) {
        super(repository);
    }

    @Override
    protected Designation newEntity() {
        return new Designation();
    }

    @Override
    protected String label() {
        return "Designation";
    }
}
