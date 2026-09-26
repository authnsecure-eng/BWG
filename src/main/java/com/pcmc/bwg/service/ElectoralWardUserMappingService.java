package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.mapping.ElectoralWardUserMappingRequest;
import com.pcmc.bwg.entity.ElectoralWardUserMapping;
import com.pcmc.bwg.exception.BadRequestException;
import com.pcmc.bwg.exception.ConflictException;
import com.pcmc.bwg.exception.ResourceNotFoundException;
import com.pcmc.bwg.repository.AppUserRepository;
import com.pcmc.bwg.repository.ElectoralWardRepository;
import com.pcmc.bwg.repository.ElectoralWardUserMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElectoralWardUserMappingService {

    private static final Logger log = LoggerFactory.getLogger(ElectoralWardUserMappingService.class);

    private final ElectoralWardUserMappingRepository mappingRepository;
    private final ElectoralWardRepository electoralWardRepository;
    private final AppUserRepository appUserRepository;

    public ElectoralWardUserMappingService(ElectoralWardUserMappingRepository mappingRepository,
                                            ElectoralWardRepository electoralWardRepository,
                                            AppUserRepository appUserRepository) {
        this.mappingRepository = mappingRepository;
        this.electoralWardRepository = electoralWardRepository;
        this.appUserRepository = appUserRepository;
    }

    @Transactional
    public ElectoralWardUserMapping create(ElectoralWardUserMappingRequest request) {
        log.info("START create electoralWardId={} appUserId={}", request.getElectoralWardId(), request.getAppUserId());
        try {
            if (!electoralWardRepository.existsById(request.getElectoralWardId())) {
                throw new BadRequestException("Electoral Ward not found with id " + request.getElectoralWardId());
            }
            if (!appUserRepository.existsById(request.getAppUserId())) {
                throw new BadRequestException("User not found with id " + request.getAppUserId());
            }

            ElectoralWardUserMapping mapping = new ElectoralWardUserMapping();
            mapping.setElectoralWardId(request.getElectoralWardId());
            mapping.setAppUserId(request.getAppUserId());

            try {
                mapping = mappingRepository.saveAndFlush(mapping);
            } catch (DataIntegrityViolationException ex) {
                throw new ConflictException("This user is already mapped to the selected electoral ward");
            }

            log.info("Electoral ward user mapping created, id: {}", mapping.getId());
            log.info("SUCCESS create id={}", mapping.getId());
            return mapping;
        } catch (RuntimeException ex) {
            log.error("ERROR create electoralWardId={} appUserId={} - {}",
                    request.getElectoralWardId(), request.getAppUserId(), ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public List<ElectoralWardUserMapping> findAll(Long electoralWardId) {
        log.info("START findAll electoralWardId={}", electoralWardId);
        try {
            List<ElectoralWardUserMapping> result = electoralWardId != null
                    ? mappingRepository.findByElectoralWardId(electoralWardId)
                    : mappingRepository.findAll();
            log.info("SUCCESS findAll count={}", result.size());
            return result;
        } catch (RuntimeException ex) {
            log.error("ERROR findAll electoralWardId={} - {}", electoralWardId, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public void delete(Long id) {
        log.info("START delete id={}", id);
        try {
            if (!mappingRepository.existsById(id)) {
                throw new ResourceNotFoundException("Mapping not found with id " + id);
            }
            mappingRepository.deleteById(id);
            log.info("Electoral ward user mapping deleted, id: {}", id);
            log.info("SUCCESS delete id={}", id);
        } catch (RuntimeException ex) {
            log.error("ERROR delete id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }
}
