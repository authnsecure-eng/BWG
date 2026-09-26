package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.Department;
import com.pcmc.bwg.service.AbstractNamedMasterService;
import com.pcmc.bwg.service.DepartmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/departments")
public class DepartmentController extends AbstractNamedMasterController<Department> {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    protected AbstractNamedMasterService<Department> service() {
        return departmentService;
    }
}
