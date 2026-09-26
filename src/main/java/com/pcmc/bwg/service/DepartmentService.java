package com.pcmc.bwg.service;

import com.pcmc.bwg.entity.Department;
import com.pcmc.bwg.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends AbstractNamedMasterService<Department> {

    public DepartmentService(DepartmentRepository repository) {
        super(repository);
    }

    @Override
    protected Department newEntity() {
        return new Department();
    }

    @Override
    protected String label() {
        return "Department";
    }
}
