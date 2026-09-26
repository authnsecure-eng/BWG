package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.user.UserResponse;
import com.pcmc.bwg.dto.user.UserStatusRequest;
import com.pcmc.bwg.dto.user.UserUpdateRequest;
import com.pcmc.bwg.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<UserResponse> create(@RequestParam Long agencyId,
                                                @RequestParam String fullName,
                                                @RequestParam String mobileNo,
                                                @RequestParam String email,
                                                @RequestParam String aadhaarNo,
                                                @RequestParam String address,
                                                @RequestParam String pinCode,
                                                @RequestParam String password,
                                                @RequestParam(required = false) MultipartFile photo) {
        log.info("START create agencyId={} mobileNo={}", agencyId, mobileNo);
        try {
            var user = userService.create(agencyId, fullName, mobileNo, email, aadhaarNo, address, pinCode, password, photo);
            ResponseEntity<UserResponse> response = ResponseEntity.ok(userService.toResponse(user));
            log.info("SUCCESS create agencyId={} mobileNo={}", agencyId, mobileNo);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR create agencyId={} mobileNo={} - {}", agencyId, mobileNo, ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(@RequestParam(required = false) Long agencyId) {
        log.info("START findAll agencyId={}", agencyId);
        try {
            List<UserResponse> response = userService.findAll(agencyId).stream()
                    .map(userService::toResponse)
                    .toList();
            log.info("SUCCESS findAll agencyId={} count={}", agencyId, response.size());
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("ERROR findAll agencyId={} - {}", agencyId, ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        log.info("START findById id={}", id);
        try {
            ResponseEntity<UserResponse> response = ResponseEntity.ok(userService.toResponse(userService.findById(id)));
            log.info("SUCCESS findById id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR findById id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        log.info("START update id={}", id);
        try {
            ResponseEntity<UserResponse> response =
                    ResponseEntity.ok(userService.toResponse(userService.update(id, request)));
            log.info("SUCCESS update id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR update id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponse> updateStatus(@PathVariable Long id,
                                                       @Valid @RequestBody UserStatusRequest request) {
        log.info("START updateStatus id={}", id);
        try {
            ResponseEntity<UserResponse> response =
                    ResponseEntity.ok(userService.toResponse(userService.updateStatus(id, request.getStatus())));
            log.info("SUCCESS updateStatus id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR updateStatus id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PostMapping(value = "/{id}/photo", consumes = "multipart/form-data")
    public ResponseEntity<UserResponse> updatePhoto(@PathVariable Long id, @RequestParam MultipartFile photo) {
        log.info("START updatePhoto id={}", id);
        try {
            ResponseEntity<UserResponse> response =
                    ResponseEntity.ok(userService.toResponse(userService.updatePhoto(id, photo)));
            log.info("SUCCESS updatePhoto id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR updatePhoto id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("START delete id={}", id);
        try {
            userService.delete(id);
            log.info("SUCCESS delete id={}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            log.error("ERROR delete id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }
}
