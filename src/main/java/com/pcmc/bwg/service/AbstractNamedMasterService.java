package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.master.NamedMasterRequest;
import com.pcmc.bwg.entity.NamedMasterEntity;
import com.pcmc.bwg.entity.enums.MasterStatus;
import com.pcmc.bwg.exception.ConflictException;
import com.pcmc.bwg.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Shared CRUD engine for simple name+status lookup masters (Zone, Department,
 * Designation, BWG Category, ...). Concrete subclasses just wire a
 * repository plus a label used in error messages/logs — see ZoneService for
 * the minimal shape.
 */
public abstract class AbstractNamedMasterService<T extends NamedMasterEntity> {

    private static final Logger log = LoggerFactory.getLogger(AbstractNamedMasterService.class);

    protected final JpaRepository<T, Long> repository;

    protected AbstractNamedMasterService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    /** Creates a new, unpersisted instance of the concrete entity type. */
    protected abstract T newEntity();

    /** Human-readable label used in error messages and log lines, e.g. "Zone". */
    protected abstract String label();

    @Transactional
    public T create(NamedMasterRequest request) {
        log.info("START create{}", label());
        try {
            T entity = newEntity();
            entity.setName(request.getName().trim());
            entity.setStatus(MasterStatus.ACTIVE);
            T saved = saveOrConflict(entity);
            log.info("{} created, id: {}", label(), saved.getId());
            log.info("SUCCESS create{} id={}", label(), saved.getId());
            return saved;
        } catch (RuntimeException ex) {
            log.error("ERROR create{} - {}", label(), ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        log.info("START findAll{}", label());
        try {
            List<T> result = repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
            log.info("SUCCESS findAll{} count={}", label(), result.size());
            return result;
        } catch (RuntimeException ex) {
            log.error("ERROR findAll{} - {}", label(), ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public T findById(Long id) {
        log.info("START findById{} id={}", label(), id);
        try {
            T entity = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(label() + " not found with id " + id));
            log.info("SUCCESS findById{} id={}", label(), id);
            return entity;
        } catch (RuntimeException ex) {
            log.error("ERROR findById{} id={} - {}", label(), id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public T update(Long id, NamedMasterRequest request) {
        log.info("START update{} id={}", label(), id);
        try {
            T entity = findById(id);
            entity.setName(request.getName().trim());
            T saved = saveOrConflict(entity);
            log.info("{} updated, id: {}", label(), saved.getId());
            log.info("SUCCESS update{} id={}", label(), id);
            return saved;
        } catch (RuntimeException ex) {
            log.error("ERROR update{} id={} - {}", label(), id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public T updateStatus(Long id, MasterStatus status) {
        log.info("START updateStatus{} id={}", label(), id);
        try {
            T entity = findById(id);
            entity.setStatus(status);
            T saved = repository.save(entity);
            log.info("{} status updated, id: {}, status: {}", label(), saved.getId(), status);
            log.info("SUCCESS updateStatus{} id={}", label(), id);
            return saved;
        } catch (RuntimeException ex) {
            log.error("ERROR updateStatus{} id={} - {}", label(), id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public void delete(Long id) {
        log.info("START delete{} id={}", label(), id);
        try {
            T entity = findById(id);
            try {
                repository.delete(entity);
                repository.flush();
            } catch (DataIntegrityViolationException ex) {
                throw new ConflictException(label() + " cannot be deleted because it is referenced by other records");
            }
            log.info("{} deleted, id: {}", label(), id);
            log.info("SUCCESS delete{} id={}", label(), id);
        } catch (RuntimeException ex) {
            log.error("ERROR delete{} id={} - {}", label(), id, ex.getMessage(), ex);
            throw ex;
        }
    }

    private T saveOrConflict(T entity) {
        try {
            return repository.saveAndFlush(entity);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException(label() + " name already exists");
        }
    }
}
