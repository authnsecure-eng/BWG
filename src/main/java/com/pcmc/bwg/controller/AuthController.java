package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.auth.AdminLoginRequest;
import com.pcmc.bwg.dto.auth.LoginResponse;
import com.pcmc.bwg.dto.auth.UserLoginRequest;
import com.pcmc.bwg.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@Valid @RequestBody AdminLoginRequest request) {
        log.info("START adminLogin username={}", request.getUsername());
        try {
            ResponseEntity<LoginResponse> response =
                    ResponseEntity.ok(authService.loginAdmin(request.getUsername(), request.getPassword()));
            log.info("SUCCESS adminLogin username={}", request.getUsername());
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR adminLogin username={} - {}", request.getUsername(), ex.getMessage(), ex);
            throw ex;
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponse> userLogin(@Valid @RequestBody UserLoginRequest request) {
        log.info("START userLogin mobileNo={}", request.getMobileNo());
        try {
            ResponseEntity<LoginResponse> response =
                    ResponseEntity.ok(authService.loginUser(request.getMobileNo(), request.getPassword()));
            log.info("SUCCESS userLogin mobileNo={}", request.getMobileNo());
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR userLogin mobileNo={} - {}", request.getMobileNo(), ex.getMessage(), ex);
            throw ex;
        }
    }
}
