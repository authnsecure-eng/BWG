package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.master.MasterStatusRequest;
import com.pcmc.bwg.dto.master.NamedChildMasterRequest;
import com.pcmc.bwg.dto.master.NamedChildMasterResponse;
import com.pcmc.bwg.entity.NamedChildMasterEntity;
import com.pcmc.bwg.service.AbstractNamedChildMasterService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Shared REST surface for cascading masters (Administrative Ward, Electoral
 * Ward, Beat, BWG Sub-Category). GET supports an optional ?parentId= filter
 * so the frontend can populate a child dropdown scoped to the selected
 * parent without fetching everything.
 */
public abstract class AbstractNamedChildMasterController<T extends NamedChildMasterEntity> {

    private static final Logger log = LoggerFactory.getLogger(AbstractNamedChildMasterController.class);

    protected abstract AbstractNamedChildMasterService<T> service();

    @PostMapping
    public ResponseEntity<NamedChildMasterResponse> create(@Valid @RequestBody NamedChildMasterRequest request) {
        log.info("START create");
        try {
            ResponseEntity<NamedChildMasterResponse> response =
                    ResponseEntity.ok(NamedChildMasterResponse.from(service().create(request)));
            log.info("SUCCESS create");
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR create - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping
    public ResponseEntity<List<NamedChildMasterResponse>> findAll(
            @RequestParam(required = false) Long parentId) {
        log.info("START findAll parentId={}", parentId);
        try {
            List<NamedChildMasterResponse> response = service().findAll(parentId).stream()
                    .map(NamedChildMasterResponse::from)
                    .toList();
            log.info("SUCCESS findAll count={}", response.size());
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("ERROR findAll parentId={} - {}", parentId, ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NamedChildMasterResponse> findById(@PathVariable Long id) {
        log.info("START findById id={}", id);
        try {
            ResponseEntity<NamedChildMasterResponse> response =
                    ResponseEntity.ok(NamedChildMasterResponse.from(service().findById(id)));
            log.info("SUCCESS findById id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR findById id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<NamedChildMasterResponse> update(@PathVariable Long id,
                                                             @Valid @RequestBody NamedChildMasterRequest request) {
        log.info("START update id={}", id);
        try {
            ResponseEntity<NamedChildMasterResponse> response =
                    ResponseEntity.ok(NamedChildMasterResponse.from(service().update(id, request)));
            log.info("SUCCESS update id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR update id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<NamedChildMasterResponse> updateStatus(@PathVariable Long id,
                                                                   @Valid @RequestBody MasterStatusRequest request) {
        log.info("START updateStatus id={}", id);
        try {
            ResponseEntity<NamedChildMasterResponse> response =
                    ResponseEntity.ok(NamedChildMasterResponse.from(service().updateStatus(id, request.getStatus())));
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
