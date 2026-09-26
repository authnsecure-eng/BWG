package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.otp.OtpResponse;
import com.pcmc.bwg.dto.otp.OtpSendRequest;
import com.pcmc.bwg.dto.otp.OtpVerifyRequest;
import com.pcmc.bwg.service.OtpService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private static final Logger log = LoggerFactory.getLogger(OtpController.class);

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/send")
    public ResponseEntity<OtpResponse> send(@Valid @RequestBody OtpSendRequest request) {
        log.info("START send mobileNo={}", request.getMobileNo());
        try {
            ResponseEntity<OtpResponse> response = ResponseEntity.ok(otpService.send(request.getMobileNo()));
            log.info("SUCCESS send mobileNo={}", request.getMobileNo());
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR send mobileNo={} - {}", request.getMobileNo(), ex.getMessage(), ex);
            throw ex;
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<OtpResponse> resend(@Valid @RequestBody OtpSendRequest request) {
        log.info("START resend mobileNo={}", request.getMobileNo());
        try {
            ResponseEntity<OtpResponse> response = ResponseEntity.ok(otpService.resend(request.getMobileNo()));
            log.info("SUCCESS resend mobileNo={}", request.getMobileNo());
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR resend mobileNo={} - {}", request.getMobileNo(), ex.getMessage(), ex);
            throw ex;
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verify(@Valid @RequestBody OtpVerifyRequest request) {
        log.info("START verify mobileNo={}", request.getMobileNo());
        try {
            otpService.verify(request.getMobileNo(), request.getOtp());
            log.info("SUCCESS verify mobileNo={}", request.getMobileNo());
            return ResponseEntity.ok(Map.of("message", "Mobile number verified successfully"));
        } catch (RuntimeException ex) {
            log.error("ERROR verify mobileNo={} - {}", request.getMobileNo(), ex.getMessage(), ex);
            throw ex;
        }
    }
}
