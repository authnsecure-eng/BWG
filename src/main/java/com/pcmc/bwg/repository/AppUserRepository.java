package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByMobileNo(String mobileNo);

    Optional<AppUser> findByEmail(String email);

    boolean existsByMobileNo(String mobileNo);

    boolean existsByEmail(String email);

    boolean existsByAadhaarNoHash(String aadhaarNoHash);

    @Query("SELECT u FROM AppUser u JOIN FETCH u.agency WHERE u.id = :id")
    Optional<AppUser> findByIdFetchAgency(@Param("id") Long id);

    @Query("SELECT u FROM AppUser u JOIN FETCH u.agency")
    List<AppUser> findAllFetchAgency();

    @Query("SELECT u FROM AppUser u JOIN FETCH u.agency WHERE u.agency.id = :agencyId")
    List<AppUser> findByAgencyIdFetchAgency(@Param("agencyId") Long agencyId);
}
