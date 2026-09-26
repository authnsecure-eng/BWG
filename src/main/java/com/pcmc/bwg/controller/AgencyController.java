package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.agency.AgencyRequest;
import com.pcmc.bwg.dto.agency.AgencyResponse;
import com.pcmc.bwg.dto.agency.AgencyStatusRequest;
import com.pcmc.bwg.service.AgencyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/agencies")
public class AgencyController {

    private static final Logger log = LoggerFactory.getLogger(AgencyController.class);

    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @PostMapping
    public ResponseEntity<AgencyResponse> create(@Valid @RequestBody AgencyRequest request) {
        log.info("START create");
        try {
            ResponseEntity<AgencyResponse> response = ResponseEntity.ok(AgencyResponse.from(agencyService.create(request)));
            log.info("SUCCESS create");
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR create - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping
    public ResponseEntity<List<AgencyResponse>> findAll() {
        log.info("START findAll");
        try {
            List<AgencyResponse> response = agencyService.findAll().stream()
                    .map(AgencyResponse::from)
                    .toList();
            log.info("SUCCESS findAll count={}", response.size());
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("ERROR findAll - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyResponse> findById(@PathVariable Long id) {
        log.info("START findById id={}", id);
        try {
            ResponseEntity<AgencyResponse> response = ResponseEntity.ok(AgencyResponse.from(agencyService.findById(id)));
            log.info("SUCCESS findById id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR findById id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgencyResponse> update(@PathVariable Long id, @Valid @RequestBody AgencyRequest request) {
        log.info("START update id={}", id);
        try {
            ResponseEntity<AgencyResponse> response = ResponseEntity.ok(AgencyResponse.from(agencyService.update(id, request)));
            log.info("SUCCESS update id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR update id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AgencyResponse> updateStatus(@PathVariable Long id,
                                                         @Valid @RequestBody AgencyStatusRequest request) {
        log.info("START updateStatus id={}", id);
        try {
            ResponseEntity<AgencyResponse> response =
                    ResponseEntity.ok(AgencyResponse.from(agencyService.updateStatus(id, request.getStatus())));
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
            agencyService.delete(id);
            log.info("SUCCESS delete id={}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            log.error("ERROR delete id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }
}
