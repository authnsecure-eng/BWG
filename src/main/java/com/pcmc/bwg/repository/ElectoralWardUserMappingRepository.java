package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.ElectoralWardUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectoralWardUserMappingRepository extends JpaRepository<ElectoralWardUserMapping, Long> {

    List<ElectoralWardUserMapping> findByElectoralWardId(Long electoralWardId);
}
