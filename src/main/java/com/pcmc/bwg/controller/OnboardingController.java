package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.onboarding.AadhaarVerifyRequest;
import com.pcmc.bwg.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/onboarding")
public class OnboardingController {

    private final UserService userService;

    public OnboardingController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/aadhaar/verify")
    public ResponseEntity<Map<String, String>> verifyAadhaar(@Valid @RequestBody AadhaarVerifyRequest request) {
        userService.verifyAadhaar(request.getUserId(), request.getAadhaarNo());
        return ResponseEntity.ok(Map.of("message", "Aadhaar verified successfully"));
    }
}
