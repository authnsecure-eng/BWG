package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.auth.AdminLoginRequest;
import com.pcmc.bwg.dto.auth.LoginResponse;
import com.pcmc.bwg.dto.auth.UserLoginRequest;
import com.pcmc.bwg.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@Valid @RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(authService.loginAdmin(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponse> userLogin(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(authService.loginUser(request.getMobileNo(), request.getPassword()));
    }
}
