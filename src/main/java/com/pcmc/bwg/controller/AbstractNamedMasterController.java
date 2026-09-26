package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.master.MasterStatusRequest;
import com.pcmc.bwg.dto.master.NamedMasterRequest;
import com.pcmc.bwg.dto.master.NamedMasterResponse;
import com.pcmc.bwg.entity.NamedMasterEntity;
import com.pcmc.bwg.service.AbstractNamedMasterService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Shared REST surface for simple name+status lookup masters. Concrete
 * subclasses only need @RestController/@RequestMapping and a service()
 * accessor - see ZoneController for the minimal shape.
 */
public abstract class AbstractNamedMasterController<T extends NamedMasterEntity> {

    private static final Logger log = LoggerFactory.getLogger(AbstractNamedMasterController.class);

    protected abstract AbstractNamedMasterService<T> service();

    @PostMapping
    public ResponseEntity<NamedMasterResponse> create(@Valid @RequestBody NamedMasterRequest request) {
        log.info("START create");
        try {
            ResponseEntity<NamedMasterResponse> response =
                    ResponseEntity.ok(NamedMasterResponse.from(service().create(request)));
            log.info("SUCCESS create");
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR create - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping
    public ResponseEntity<List<NamedMasterResponse>> findAll() {
        log.info("START findAll");
        try {
            List<NamedMasterResponse> response = service().findAll().stream()
                    .map(NamedMasterResponse::from)
                    .toList();
            log.info("SUCCESS findAll count={}", response.size());
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("ERROR findAll - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NamedMasterResponse> findById(@PathVariable Long id) {
        log.info("START findById id={}", id);
        try {
            ResponseEntity<NamedMasterResponse> response =
                    ResponseEntity.ok(NamedMasterResponse.from(service().findById(id)));
            log.info("SUCCESS findById id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR findById id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<NamedMasterResponse> update(@PathVariable Long id,
                                                        @Valid @RequestBody NamedMasterRequest request) {
        log.info("START update id={}", id);
        try {
            ResponseEntity<NamedMasterResponse> response =
                    ResponseEntity.ok(NamedMasterResponse.from(service().update(id, request)));
            log.info("SUCCESS update id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR update id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<NamedMasterResponse> updateStatus(@PathVariable Long id,
                                                              @Valid @RequestBody MasterStatusRequest request) {
        log.info("START updateStatus id={}", id);
        try {
            ResponseEntity<NamedMasterResponse> response =
                    ResponseEntity.ok(NamedMasterResponse.from(service().updateStatus(id, request.getStatus())));
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
            service().delete(id);
            log.info("SUCCESS delete id={}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            log.error("ERROR delete id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }
}
