package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
