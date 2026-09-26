package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.ElectoralWard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectoralWardRepository extends JpaRepository<ElectoralWard, Long> {

    List<ElectoralWard> findByParentId(Long parentId);
}
