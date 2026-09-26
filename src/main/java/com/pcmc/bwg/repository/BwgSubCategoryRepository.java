package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.BwgSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BwgSubCategoryRepository extends JpaRepository<BwgSubCategory, Long> {

    List<BwgSubCategory> findByParentId(Long parentId);
}
