package com.pcmc.bwg.controller;

import com.pcmc.bwg.config.InternalBridgeProperties;
import com.pcmc.bwg.dto.ingest.SurveyIngestRequest;
import com.pcmc.bwg.entity.BwgSurvey;
import com.pcmc.bwg.exception.UnauthorizedException;
import com.pcmc.bwg.service.SurveyIngestService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Receives survey submissions pushed from the mobile app backend
 * (AdminBridgeClient there), so they land in bwg_survey and show up in the
 * Admin "Reports" screen.
 *
 * This is a server-to-server endpoint, not an admin-user endpoint: it is
 * authenticated with a shared API key (app.internal-bridge.api-key) instead
 * of the admin JWT, and SecurityConfig permits /api/internal/** without a
 * ROLE_ADMIN token. On top of the API key, this path must also be blocked
 * at the firewall/network level from the public internet - see the hosting
 * requirement spec (port 8090 is internal-only).
 */
@RestController
@RequestMapping("/api/internal/surveys")
public class SurveyIngestController {

    private static final Logger log = LoggerFactory.getLogger(SurveyIngestController.class);

    private final SurveyIngestService surveyIngestService;
    private final InternalBridgeProperties internalBridgeProperties;

    public SurveyIngestController(SurveyIngestService surveyIngestService,
                                   InternalBridgeProperties internalBridgeProperties) {
        this.surveyIngestService = surveyIngestService;
        this.internalBridgeProperties = internalBridgeProperties;
    }

    @PostMapping("/ingest")
    public ResponseEntity<Map<String, Object>> ingest(
            @RequestHeader(value = "X-Internal-Api-Key", required = false) String apiKey,
            @Valid @RequestBody SurveyIngestRequest request) {

        checkApiKey(apiKey);

        BwgSurvey saved = surveyIngestService.ingest(request);

        log.info("START ingest mobileSurveyId={}", request.getMobileSurveyId());
        Map<String, Object> body = Map.of(
                "bwgSurveyId", saved.getId(),
                "applicationNo", saved.getApplicationNo(),
                "status", saved.getStatus()
        );
        log.info("SUCCESS ingest mobileSurveyId={} -> bwgSurveyId={}", request.getMobileSurveyId(), saved.getId());
        return ResponseEntity.ok(body);
    }

    private void checkApiKey(String suppliedKey) {
        String expectedKey = internalBridgeProperties.getApiKey();
        if (!internalBridgeProperties.isEnabled()) {
            throw new UnauthorizedException("Mobile survey ingest is disabled");
        }
        if (expectedKey == null || expectedKey.isBlank()) {
            log.error("app.internal-bridge.api-key is not configured; refusing all ingest calls");
            throw new UnauthorizedException("Ingest endpoint is not configured");
        }
        if (suppliedKey == null || !expectedKey.equals(suppliedKey)) {
            throw new UnauthorizedException("Invalid or missing X-Internal-Api-Key");
        }
    }
}
