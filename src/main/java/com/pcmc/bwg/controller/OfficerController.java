package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.master.MasterStatusRequest;
import com.pcmc.bwg.dto.officer.OfficerRequest;
import com.pcmc.bwg.dto.officer.OfficerResponse;
import com.pcmc.bwg.service.OfficerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/masters/officers")
public class OfficerController {

    private static final Logger log = LoggerFactory.getLogger(OfficerController.class);

    private final OfficerService officerService;

    public OfficerController(OfficerService officerService) {
        this.officerService = officerService;
    }

    @PostMapping
    public ResponseEntity<OfficerResponse> create(@Valid @RequestBody OfficerRequest request) {
        log.info("START create");
        try {
            ResponseEntity<OfficerResponse> response = ResponseEntity.ok(OfficerResponse.from(officerService.create(request)));
            log.info("SUCCESS create");
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR create - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping
    public ResponseEntity<List<OfficerResponse>> findAll() {
        log.info("START findAll");
        try {
            List<OfficerResponse> response = officerService.findAll().stream()
                    .map(OfficerResponse::from)
                    .toList();
            log.info("SUCCESS findAll count={}", response.size());
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("ERROR findAll - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficerResponse> findById(@PathVariable Long id) {
        log.info("START findById id={}", id);
        try {
            ResponseEntity<OfficerResponse> response = ResponseEntity.ok(OfficerResponse.from(officerService.findById(id)));
            log.info("SUCCESS findById id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR findById id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficerResponse> update(@PathVariable Long id, @Valid @RequestBody OfficerRequest request) {
        log.info("START update id={}", id);
        try {
            ResponseEntity<OfficerResponse> response = ResponseEntity.ok(OfficerResponse.from(officerService.update(id, request)));
            log.info("SUCCESS update id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR update id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OfficerResponse> updateStatus(@PathVariable Long id,
                                                          @Valid @RequestBody MasterStatusRequest request) {
        log.info("START updateStatus id={}", id);
        try {
            ResponseEntity<OfficerResponse> response =
                    ResponseEntity.ok(OfficerResponse.from(officerService.updateStatus(id, request.getStatus())));
            log.info("SUCCESS updateStatus id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR updateStatus id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("START delete id={}", id);
        try {
            officerService.delete(id);
            log.info("SUCCESS delete id={}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            log.error("ERROR delete id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }
}
