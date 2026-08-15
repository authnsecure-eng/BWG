package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.OtpVerification;
import com.pcmc.bwg.entity.enums.OtpPurpose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findFirstByUserIdAndPurposeOrderByCreatedAtDesc(Long userId, OtpPurpose purpose);
}
