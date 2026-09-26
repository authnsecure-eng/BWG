package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficerRepository extends JpaRepository<Officer, Long> {

    boolean existsByMobileNo(String mobileNo);
}
