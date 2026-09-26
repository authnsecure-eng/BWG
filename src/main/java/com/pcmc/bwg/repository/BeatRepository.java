package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.Beat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeatRepository extends JpaRepository<Beat, Long> {

    List<Beat> findByParentId(Long parentId);
}
