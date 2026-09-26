package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.admin.AdminChangePasswordRequest;
import com.pcmc.bwg.dto.admin.AdminProfileUpdateRequest;
import com.pcmc.bwg.entity.Admin;
import com.pcmc.bwg.exception.BadRequestException;
import com.pcmc.bwg.exception.ResourceNotFoundException;
import com.pcmc.bwg.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdminProfileService {

    private static final Logger log = LoggerFactory.getLogger(AdminProfileService.class);

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    public AdminProfileService(AdminRepository adminRepository, PasswordEncoder passwordEncoder,
                                FileStorageService fileStorageService) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    public Admin getProfile(Long adminId) {
        log.info("START getProfile adminId={}", adminId);
        try {
            Admin admin = adminRepository.findById(adminId)
                    .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id " + adminId));
            log.info("SUCCESS getProfile adminId={}", adminId);
            return admin;
        } catch (RuntimeException ex) {
            log.error("ERROR getProfile adminId={} - {}", adminId, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public Admin updateProfile(Long adminId, AdminProfileUpdateRequest request) {
        log.info("START updateProfile adminId={}", adminId);
        try {
            Admin admin = getProfile(adminId);
            admin.setFullName(request.getFullName());
            admin.setEmail(request.getEmail());
            admin.setMobileNo(request.getMobileNo());
            admin = adminRepository.save(admin);

            log.info("Admin profile updated, adminId: {}", adminId);
            log.info("SUCCESS updateProfile adminId={}", adminId);

            return admin;
        } catch (RuntimeException ex) {
            log.error("ERROR updateProfile adminId={} - {}", adminId, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public Admin updatePhoto(Long adminId, MultipartFile photo) {
        log.info("START updatePhoto adminId={}", adminId);
        try {
            Admin admin = getProfile(adminId);
            admin.setPhotoPath(fileStorageService.store(photo));
            admin = adminRepository.save(admin);

            log.info("Admin photo updated, adminId: {}", adminId);
            log.info("SUCCESS updatePhoto adminId={}", adminId);

            return admin;
        } catch (RuntimeException ex) {
            log.error("ERROR updatePhoto adminId={} - {}", adminId, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public void changePassword(Long adminId, AdminChangePasswordRequest request) {
        log.info("START changePassword adminId={}", adminId);
        try {
            Admin admin = getProfile(adminId);
            if (!passwordEncoder.matches(request.getCurrentPassword(), admin.getPasswordHash())) {
                log.warn("Admin password change rejected, current password mismatch for adminId: {}", adminId);
                throw new BadRequestException("Current password is incorrect");
            }
            admin.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
            adminRepository.save(admin);

            log.info("Admin password changed, adminId: {}", adminId);
            log.info("SUCCESS changePassword adminId={}", adminId);
        } catch (RuntimeException ex) {
            log.error("ERROR changePassword adminId={} - {}", adminId, ex.getMessage(), ex);
            throw ex;
        }
    }
}
