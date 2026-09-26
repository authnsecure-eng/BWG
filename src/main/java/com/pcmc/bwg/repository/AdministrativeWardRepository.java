package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.AdministrativeWard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdministrativeWardRepository extends JpaRepository<AdministrativeWard, Long> {

    List<AdministrativeWard> findByParentId(Long parentId);
}
