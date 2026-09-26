package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.mapping.ElectoralWardUserMappingRequest;
import com.pcmc.bwg.dto.mapping.ElectoralWardUserMappingResponse;
import com.pcmc.bwg.service.ElectoralWardUserMappingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/masters/electoral-ward-user-mappings")
public class ElectoralWardUserMappingController {

    private static final Logger log = LoggerFactory.getLogger(ElectoralWardUserMappingController.class);

    private final ElectoralWardUserMappingService mappingService;

    public ElectoralWardUserMappingController(ElectoralWardUserMappingService mappingService) {
        this.mappingService = mappingService;
    }

    @PostMapping
    public ResponseEntity<ElectoralWardUserMappingResponse> create(
            @Valid @RequestBody ElectoralWardUserMappingRequest request) {
        log.info("START create");
        try {
            ResponseEntity<ElectoralWardUserMappingResponse> response =
                    ResponseEntity.ok(ElectoralWardUserMappingResponse.from(mappingService.create(request)));
            log.info("SUCCESS create");
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR create - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping
    public ResponseEntity<List<ElectoralWardUserMappingResponse>> findAll(
            @RequestParam(required = false) Long electoralWardId) {
        log.info("START findAll electoralWardId={}", electoralWardId);
        try {
            List<ElectoralWardUserMappingResponse> response = mappingService.findAll(electoralWardId).stream()
                    .map(ElectoralWardUserMappingResponse::from)
                    .toList();
            log.info("SUCCESS findAll count={}", response.size());
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("ERROR findAll electoralWardId={} - {}", electoralWardId, ex.getMessage(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("START delete id={}", id);
        try {
            mappingService.delete(id);
            log.info("SUCCESS delete id={}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            log.error("ERROR delete id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }
}
