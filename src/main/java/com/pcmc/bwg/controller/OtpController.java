package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.otp.OtpResponse;
import com.pcmc.bwg.dto.otp.OtpSendRequest;
import com.pcmc.bwg.dto.otp.OtpVerifyRequest;
import com.pcmc.bwg.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/send")
    public ResponseEntity<OtpResponse> send(@Valid @RequestBody OtpSendRequest request) {
        return ResponseEntity.ok(otpService.send(request.getMobileNo()));
    }

    @PostMapping("/resend")
    public ResponseEntity<OtpResponse> resend(@Valid @RequestBody OtpSendRequest request) {
        return ResponseEntity.ok(otpService.resend(request.getMobileNo()));
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verify(@Valid @RequestBody OtpVerifyRequest request) {
        otpService.verify(request.getMobileNo(), request.getOtp());
        return ResponseEntity.ok(Map.of("message", "Mobile number verified successfully"));
    }
}
