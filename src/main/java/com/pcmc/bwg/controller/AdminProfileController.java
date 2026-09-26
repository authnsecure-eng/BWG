package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.admin.AdminChangePasswordRequest;
import com.pcmc.bwg.dto.admin.AdminProfileResponse;
import com.pcmc.bwg.dto.admin.AdminProfileUpdateRequest;
import com.pcmc.bwg.service.AdminProfileService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/profile")
public class AdminProfileController {

    private static final Logger log = LoggerFactory.getLogger(AdminProfileController.class);

    private final AdminProfileService adminProfileService;

    public AdminProfileController(AdminProfileService adminProfileService) {
        this.adminProfileService = adminProfileService;
    }

    private Long currentAdminId(Authentication authentication) {
        return Long.valueOf(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<AdminProfileResponse> getProfile(Authentication authentication) {
        Long adminId = currentAdminId(authentication);
        log.info("START getProfile adminId={}", adminId);
        try {
            ResponseEntity<AdminProfileResponse> response =
                    ResponseEntity.ok(AdminProfileResponse.from(adminProfileService.getProfile(adminId)));
            log.info("SUCCESS getProfile adminId={}", adminId);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR getProfile adminId={} - {}", adminId, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PutMapping
    public ResponseEntity<AdminProfileResponse> updateProfile(Authentication authentication,
                                                                @Valid @RequestBody AdminProfileUpdateRequest request) {
        Long adminId = currentAdminId(authentication);
        log.info("START updateProfile adminId={}", adminId);
        try {
            var admin = adminProfileService.updateProfile(adminId, request);
            ResponseEntity<AdminProfileResponse> response = ResponseEntity.ok(AdminProfileResponse.from(admin));
            log.info("SUCCESS updateProfile adminId={}", adminId);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR updateProfile adminId={} - {}", adminId, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PostMapping(value = "/photo", consumes = "multipart/form-data")
    public ResponseEntity<AdminProfileResponse> updatePhoto(Authentication authentication,
                                                              @RequestParam MultipartFile photo) {
        Long adminId = currentAdminId(authentication);
        log.info("START updatePhoto adminId={}", adminId);
        try {
            var admin = adminProfileService.updatePhoto(adminId, photo);
            ResponseEntity<AdminProfileResponse> response = ResponseEntity.ok(AdminProfileResponse.from(admin));
            log.info("SUCCESS updatePhoto adminId={}", adminId);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR updatePhoto adminId={} - {}", adminId, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(Authentication authentication,
                                                @Valid @RequestBody AdminChangePasswordRequest request) {
        Long adminId = currentAdminId(authentication);
        log.info("START changePassword adminId={}", adminId);
        try {
            adminProfileService.changePassword(adminId, request);
            log.info("SUCCESS changePassword adminId={}", adminId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            log.error("ERROR changePassword adminId={} - {}", adminId, ex.getMessage(), ex);
            throw ex;
        }
    }
}
