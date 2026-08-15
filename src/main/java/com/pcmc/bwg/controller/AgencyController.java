package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.agency.AgencyRequest;
import com.pcmc.bwg.dto.agency.AgencyResponse;
import com.pcmc.bwg.dto.agency.AgencyStatusRequest;
import com.pcmc.bwg.service.AgencyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/agencies")
public class AgencyController {

    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @PostMapping
    public ResponseEntity<AgencyResponse> create(@Valid @RequestBody AgencyRequest request) {
        return ResponseEntity.ok(AgencyResponse.from(agencyService.create(request)));
    }

    @GetMapping
    public ResponseEntity<List<AgencyResponse>> findAll() {
        List<AgencyResponse> response = agencyService.findAll().stream()
                .map(AgencyResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(AgencyResponse.from(agencyService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgencyResponse> update(@PathVariable Long id, @Valid @RequestBody AgencyRequest request) {
        return ResponseEntity.ok(AgencyResponse.from(agencyService.update(id, request)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AgencyResponse> updateStatus(@PathVariable Long id,
                                                         @Valid @RequestBody AgencyStatusRequest request) {
        return ResponseEntity.ok(AgencyResponse.from(agencyService.updateStatus(id, request.getStatus())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agencyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
