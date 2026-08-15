package com.pcmc.bwg.service;

import com.pcmc.bwg.config.OtpProperties;
import com.pcmc.bwg.dto.otp.OtpResponse;
import com.pcmc.bwg.entity.AppUser;
import com.pcmc.bwg.entity.OtpVerification;
import com.pcmc.bwg.entity.enums.OtpPurpose;
import com.pcmc.bwg.exception.BadRequestException;
import com.pcmc.bwg.exception.ConflictException;
import com.pcmc.bwg.exception.ResourceNotFoundException;
import com.pcmc.bwg.repository.AppUserRepository;
import com.pcmc.bwg.repository.OtpVerificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class OtpService {

    private static final Logger log = LoggerFactory.getLogger(OtpService.class);
    private static final SecureRandom RANDOM = new SecureRandom();

    private final OtpProperties otpProperties;
    private final AppUserRepository appUserRepository;
    private final OtpVerificationRepository otpVerificationRepository;
    private final PasswordEncoder passwordEncoder;

    public OtpService(OtpProperties otpProperties, AppUserRepository appUserRepository,
                       OtpVerificationRepository otpVerificationRepository, PasswordEncoder passwordEncoder) {
        this.otpProperties = otpProperties;
        this.appUserRepository = appUserRepository;
        this.otpVerificationRepository = otpVerificationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public OtpResponse send(String mobileNo) {
        AppUser user = findUserByMobile(mobileNo);
        if (user.isMobileVerified()) {
            log.warn("OTP send rejected, mobile already verified for userId: {}", user.getId());
            throw new ConflictException("Mobile number is already verified");
        }

        String otp = generateOtp();
        OtpVerification record = new OtpVerification();
        record.setUser(user);
        record.setPurpose(OtpPurpose.MOBILE_VERIFICATION);
        record.setOtpHash(passwordEncoder.encode(otp));
        record.setExpiresAt(LocalDateTime.now().plusMinutes(otpProperties.getExpiryMinutes()));
        record.setLastSentAt(LocalDateTime.now());
        record.setAttemptCount(0);
        record.setResendCount(0);
        record.setVerified(false);
        otpVerificationRepository.save(record);

        log.info("OTP sent for userId: {}, purpose: {}", user.getId(), OtpPurpose.MOBILE_VERIFICATION);

        return new OtpResponse("OTP sent successfully", otpProperties.getExpiryMinutes(), otp);
    }

    @Transactional
    public OtpResponse resend(String mobileNo) {
        AppUser user = findUserByMobile(mobileNo);
        if (user.isMobileVerified()) {
            log.warn("OTP resend rejected, mobile already verified for userId: {}", user.getId());
            throw new ConflictException("Mobile number is already verified");
        }

        OtpVerification record = otpVerificationRepository
                .findFirstByUserIdAndPurposeOrderByCreatedAtDesc(user.getId(), OtpPurpose.MOBILE_VERIFICATION)
                .orElseThrow(() -> new BadRequestException("No OTP request found. Use /api/otp/send first"));

        if (record.isVerified()) {
            log.warn("OTP resend rejected, mobile already verified for userId: {}", user.getId());
            throw new ConflictException("Mobile number is already verified");
        }

        long secondsSinceLastSend = Duration.between(record.getLastSentAt(), LocalDateTime.now()).getSeconds();
        if (secondsSinceLastSend < otpProperties.getResendCooldownSeconds()) {
            long wait = otpProperties.getResendCooldownSeconds() - secondsSinceLastSend;
            log.warn("OTP resend rejected, cooldown active for userId: {}, waitSeconds: {}", user.getId(), wait);
            throw new BadRequestException("Please wait " + wait + " seconds before requesting another OTP");
        }

        if (record.getResendCount() >= otpProperties.getMaxResend()) {
            log.warn("OTP resend rejected, max resend attempts reached for userId: {}", user.getId());
            throw new BadRequestException("Maximum OTP resend attempts reached");
        }

        String otp = generateOtp();
        record.setOtpHash(passwordEncoder.encode(otp));
        record.setExpiresAt(LocalDateTime.now().plusMinutes(otpProperties.getExpiryMinutes()));
        record.setLastSentAt(LocalDateTime.now());
        record.setResendCount(record.getResendCount() + 1);
        record.setAttemptCount(0);
        otpVerificationRepository.save(record);

        log.info("OTP resent for userId: {}, purpose: {}, resendCount: {}", user.getId(),
                OtpPurpose.MOBILE_VERIFICATION, record.getResendCount());

        return new OtpResponse("OTP resent successfully", otpProperties.getExpiryMinutes(), otp);
    }

    @Transactional
    public void verify(String mobileNo, String otp) {
        AppUser user = findUserByMobile(mobileNo);
        if (user.isMobileVerified()) {
            log.warn("OTP verification rejected, mobile already verified for userId: {}", user.getId());
            throw new ConflictException("Mobile number is already verified");
        }

        OtpVerification record = otpVerificationRepository
                .findFirstByUserIdAndPurposeOrderByCreatedAtDesc(user.getId(), OtpPurpose.MOBILE_VERIFICATION)
                .orElseThrow(() -> new BadRequestException("No OTP request found. Use /api/otp/send first"));

        if (record.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.warn("OTP verification failed, OTP expired for userId: {}", user.getId());
            throw new BadRequestException("OTP has expired. Please request a new one");
        }
        if (record.getAttemptCount() >= otpProperties.getMaxAttempts()) {
            log.warn("OTP verification failed, max attempts exceeded for userId: {}", user.getId());
            throw new BadRequestException("Maximum verification attempts exceeded. Please request a new OTP");
        }

        if (!passwordEncoder.matches(otp, record.getOtpHash())) {
            record.setAttemptCount(record.getAttemptCount() + 1);
            otpVerificationRepository.save(record);
            int remaining = otpProperties.getMaxAttempts() - record.getAttemptCount();
            log.warn("OTP verification failed, invalid OTP for userId: {}, attemptsRemaining: {}",
                    user.getId(), Math.max(remaining, 0));
            throw new BadRequestException("Invalid OTP. " + Math.max(remaining, 0) + " attempt(s) remaining");
        }

        record.setVerified(true);
        otpVerificationRepository.save(record);

        user.setMobileVerified(true);
        appUserRepository.save(user);

        log.info("Mobile verified successfully for userId: {}", user.getId());
    }

    private AppUser findUserByMobile(String mobileNo) {
        return appUserRepository.findByMobileNo(mobileNo)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with mobileNo " + mobileNo));
    }

    private String generateOtp() {
        int length = otpProperties.getLength();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }
}
