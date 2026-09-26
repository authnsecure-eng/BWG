package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.agency.AgencyRequest;
import com.pcmc.bwg.entity.Agency;
import com.pcmc.bwg.entity.enums.AgencyStatus;
import com.pcmc.bwg.exception.ConflictException;
import com.pcmc.bwg.exception.ResourceNotFoundException;
import com.pcmc.bwg.repository.AgencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgencyService {

    private static final Logger log = LoggerFactory.getLogger(AgencyService.class);

    private final AgencyRepository agencyRepository;

    public AgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    @Transactional
    public Agency create(AgencyRequest request) {
        log.info("START create agencyCode={}", request.getAgencyCode());
        try {
            if (agencyRepository.existsByAgencyName(request.getAgencyName())) {
                log.warn("Agency creation rejected, duplicate agencyName: {}", request.getAgencyName());
                throw new ConflictException("Agency name already exists");
            }
            if (agencyRepository.existsByAgencyCode(request.getAgencyCode())) {
                log.warn("Agency creation rejected, duplicate agencyCode: {}", request.getAgencyCode());
                throw new ConflictException("Agency code already exists");
            }

            Agency agency = new Agency();
            applyFields(agency, request);
            agency.setStatus(AgencyStatus.ACTIVE);
            agency = agencyRepository.save(agency);

            log.info("Agency created successfully, agencyId: {}, agencyCode: {}", agency.getId(), agency.getAgencyCode());
            log.info("SUCCESS create agencyId={}", agency.getId());

            return agency;
        } catch (RuntimeException ex) {
            log.error("ERROR create agencyCode={} - {}", request.getAgencyCode(), ex.getMessage(), ex);
            throw ex;
        }
    }

    public List<Agency> findAll() {
        log.info("START findAll");
        try {
            List<Agency> result = agencyRepository.findAll();
            log.info("SUCCESS findAll count={}", result.size());
            return result;
        } catch (RuntimeException ex) {
            log.error("ERROR findAll - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    public Agency findById(Long id) {
        log.info("START findById id={}", id);
        try {
            Agency agency = agencyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id " + id));
            log.info("SUCCESS findById id={}", id);
            return agency;
        } catch (RuntimeException ex) {
            log.error("ERROR findById id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public Agency update(Long id, AgencyRequest request) {
        log.info("START update id={}", id);
        try {
            Agency agency = findById(id);

            if (!agency.getAgencyName().equals(request.getAgencyName())
                    && agencyRepository.existsByAgencyName(request.getAgencyName())) {
                log.warn("Agency update rejected, duplicate agencyName: {}", request.getAgencyName());
                throw new ConflictException("Agency name already exists");
            }
            if (!agency.getAgencyCode().equals(request.getAgencyCode())
                    && agencyRepository.existsByAgencyCode(request.getAgencyCode())) {
                log.warn("Agency update rejected, duplicate agencyCode: {}", request.getAgencyCode());
                throw new ConflictException("Agency code already exists");
            }

            applyFields(agency, request);
            agency = agencyRepository.save(agency);

            log.info("Agency updated successfully, agencyId: {}", agency.getId());
            log.info("SUCCESS update id={}", id);

            return agency;
        } catch (RuntimeException ex) {
            log.error("ERROR update id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public Agency updateStatus(Long id, AgencyStatus status) {
        log.info("START updateStatus id={}", id);
        try {
            Agency agency = findById(id);
            agency.setStatus(status);
            agency = agencyRepository.save(agency);

            log.info("Agency status updated, agencyId: {}, status: {}", agency.getId(), status);
            log.info("SUCCESS updateStatus id={}", id);

            return agency;
        } catch (RuntimeException ex) {
            log.error("ERROR updateStatus id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public void delete(Long id) {
        log.info("START delete id={}", id);
        try {
            Agency agency = findById(id);
            agencyRepository.delete(agency);

            log.info("Agency deleted, agencyId: {}", id);
            log.info("SUCCESS delete id={}", id);
        } catch (RuntimeException ex) {
            log.error("ERROR delete id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    private void applyFields(Agency agency, AgencyRequest request) {
        agency.setAgencyName(request.getAgencyName());
        agency.setAgencyCode(request.getAgencyCode());
        agency.setContactPersonName(request.getContactPersonName());
        agency.setMobileNo(request.getMobileNo());
        agency.setEmail(request.getEmail());
        agency.setAddress(request.getAddress());
        agency.setPinCode(request.getPinCode());
    }
}
