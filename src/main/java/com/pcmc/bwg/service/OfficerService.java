package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.officer.OfficerRequest;
import com.pcmc.bwg.entity.Officer;
import com.pcmc.bwg.entity.enums.MasterStatus;
import com.pcmc.bwg.exception.BadRequestException;
import com.pcmc.bwg.exception.ConflictException;
import com.pcmc.bwg.exception.ResourceNotFoundException;
import com.pcmc.bwg.repository.AdministrativeWardRepository;
import com.pcmc.bwg.repository.DepartmentRepository;
import com.pcmc.bwg.repository.DesignationRepository;
import com.pcmc.bwg.repository.OfficerRepository;
import com.pcmc.bwg.repository.ZoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OfficerService {

    private static final Logger log = LoggerFactory.getLogger(OfficerService.class);

    private final OfficerRepository officerRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final ZoneRepository zoneRepository;
    private final AdministrativeWardRepository administrativeWardRepository;

    public OfficerService(OfficerRepository officerRepository, DepartmentRepository departmentRepository,
                           DesignationRepository designationRepository, ZoneRepository zoneRepository,
                           AdministrativeWardRepository administrativeWardRepository) {
        this.officerRepository = officerRepository;
        this.departmentRepository = departmentRepository;
        this.designationRepository = designationRepository;
        this.zoneRepository = zoneRepository;
        this.administrativeWardRepository = administrativeWardRepository;
    }

    @Transactional
    public Officer create(OfficerRequest request) {
        log.info("START create");
        try {
            validateReferences(request);
            Officer officer = new Officer();
            applyFields(officer, request);
            officer.setStatus(MasterStatus.ACTIVE);
            Officer saved = saveOrConflict(officer);
            log.info("Officer created, id: {}", saved.getId());
            log.info("SUCCESS create id={}", saved.getId());
            return saved;
        } catch (RuntimeException ex) {
            log.error("ERROR create - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public List<Officer> findAll() {
        log.info("START findAll");
        try {
            List<Officer> result = officerRepository.findAll(Sort.by(Sort.Direction.ASC, "fullName"));
            log.info("SUCCESS findAll count={}", result.size());
            return result;
        } catch (RuntimeException ex) {
            log.error("ERROR findAll - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public Officer findById(Long id) {
        log.info("START findById id={}", id);
        try {
            Officer officer = officerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Officer not found with id " + id));
            log.info("SUCCESS findById id={}", id);
            return officer;
        } catch (RuntimeException ex) {
            log.error("ERROR findById id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public Officer update(Long id, OfficerRequest request) {
        log.info("START update id={}", id);
        try {
            validateReferences(request);
            Officer officer = findById(id);
            applyFields(officer, request);
            Officer saved = saveOrConflict(officer);
            log.info("Officer updated, id: {}", saved.getId());
            log.info("SUCCESS update id={}", id);
            return saved;
        } catch (RuntimeException ex) {
            log.error("ERROR update id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public Officer updateStatus(Long id, MasterStatus status) {
        log.info("START updateStatus id={}", id);
        try {
            Officer officer = findById(id);
            officer.setStatus(status);
            Officer saved = officerRepository.save(officer);
            log.info("Officer status updated, id: {}, status: {}", saved.getId(), status);
            log.info("SUCCESS updateStatus id={}", id);
            return saved;
        } catch (RuntimeException ex) {
            log.error("ERROR updateStatus id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public void delete(Long id) {
        log.info("START delete id={}", id);
        try {
            Officer officer = findById(id);
            officerRepository.delete(officer);
            log.info("Officer deleted, id: {}", id);
            log.info("SUCCESS delete id={}", id);
        } catch (RuntimeException ex) {
            log.error("ERROR delete id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    private void validateReferences(OfficerRequest request) {
        if (!departmentRepository.existsById(request.getDepartmentId())) {
            throw new BadRequestException("Department not found with id " + request.getDepartmentId());
        }
        if (!designationRepository.existsById(request.getDesignationId())) {
            throw new BadRequestException("Designation not found with id " + request.getDesignationId());
        }
        if (request.getZoneId() != null && !zoneRepository.existsById(request.getZoneId())) {
            throw new BadRequestException("Zone not found with id " + request.getZoneId());
        }
        if (request.getAdministrativeWardId() != null
                && !administrativeWardRepository.existsById(request.getAdministrativeWardId())) {
            throw new BadRequestException("Administrative Ward not found with id " + request.getAdministrativeWardId());
        }
    }

    private void applyFields(Officer officer, OfficerRequest request) {
        officer.setFullName(request.getFullName().trim());
        officer.setMobileNo(request.getMobileNo().trim());
        officer.setEmail(request.getEmail() != null ? request.getEmail().trim() : null);
        officer.setDepartmentId(request.getDepartmentId());
        officer.setDesignationId(request.getDesignationId());
        officer.setZoneId(request.getZoneId());
        officer.setAdministrativeWardId(request.getAdministrativeWardId());
    }

    private Officer saveOrConflict(Officer officer) {
        try {
            return officerRepository.saveAndFlush(officer);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Officer mobile number already exists");
        }
    }
}
