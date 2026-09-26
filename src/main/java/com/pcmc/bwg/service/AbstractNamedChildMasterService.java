package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.master.NamedChildMasterRequest;
import com.pcmc.bwg.entity.NamedChildMasterEntity;
import com.pcmc.bwg.entity.enums.MasterStatus;
import com.pcmc.bwg.exception.BadRequestException;
import com.pcmc.bwg.exception.ConflictException;
import com.pcmc.bwg.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Shared CRUD engine for cascading masters that belong to a parent master
 * (Administrative Ward -> Zone, Electoral Ward -> Administrative Ward, Beat
 * -> Electoral Ward, BWG Sub-Category -> BWG Category). The database enforces
 * the real foreign key with ON DELETE CASCADE, so deleting a parent
 * automatically removes its children - this service only needs to validate
 * the parent exists on create/update.
 */
public abstract class AbstractNamedChildMasterService<T extends NamedChildMasterEntity> {

    private static final Logger log = LoggerFactory.getLogger(AbstractNamedChildMasterService.class);

    protected final JpaRepository<T, Long> repository;
    private final Function<Long, List<T>> findByParentId;
    private final Predicate<Long> parentExists;
    private final String parentLabel;

    protected AbstractNamedChildMasterService(JpaRepository<T, Long> repository,
                                               Function<Long, List<T>> findByParentId,
                                               Predicate<Long> parentExists,
                                               String parentLabel) {
        this.repository = repository;
        this.findByParentId = findByParentId;
        this.parentExists = parentExists;
        this.parentLabel = parentLabel;
    }

    protected abstract T newEntity();

    protected abstract String label();

    @Transactional
    public T create(NamedChildMasterRequest request) {
        log.info("START create{}", label());
        try {
            validateParent(request.getParentId());
            T entity = newEntity();
            entity.setParentId(request.getParentId());
            entity.setName(request.getName().trim());
            entity.setStatus(MasterStatus.ACTIVE);
            T saved = saveOrConflict(entity);
            log.info("{} created, id: {}, parentId: {}", label(), saved.getId(), saved.getParentId());
            log.info("SUCCESS create{} id={}", label(), saved.getId());
            return saved;
        } catch (RuntimeException ex) {
            log.error("ERROR create{} - {}", label(), ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public List<T> findAll(Long parentId) {
        log.info("START findAll{} parentId={}", label(), parentId);
        try {
            List<T> result = parentId != null
                    ? findByParentId.apply(parentId)
                    : repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
            log.info("SUCCESS findAll{} count={}", label(), result.size());
            return result;
        } catch (RuntimeException ex) {
            log.error("ERROR findAll{} parentId={} - {}", label(), parentId, ex.getMessage(), ex);
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
    public T update(Long id, NamedChildMasterRequest request) {
        log.info("START update{} id={}", label(), id);
        try {
            validateParent(request.getParentId());
            T entity = findById(id);
            entity.setParentId(request.getParentId());
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
            repository.delete(entity);
            repository.flush();
            log.info("{} deleted (cascading to children), id: {}", label(), id);
            log.info("SUCCESS delete{} id={}", label(), id);
        } catch (RuntimeException ex) {
            log.error("ERROR delete{} id={} - {}", label(), id, ex.getMessage(), ex);
            throw ex;
        }
    }

    private void validateParent(Long parentId) {
        if (parentId == null || !parentExists.test(parentId)) {
            throw new BadRequestException(parentLabel + " not found with id " + parentId);
        }
    }

    private T saveOrConflict(T entity) {
        try {
            return repository.saveAndFlush(entity);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException(label() + " name already exists under the selected " + parentLabel.toLowerCase());
        }
    }
}
