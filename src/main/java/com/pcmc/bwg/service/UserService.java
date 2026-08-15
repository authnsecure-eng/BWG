package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.user.UserUpdateRequest;
import com.pcmc.bwg.entity.Agency;
import com.pcmc.bwg.entity.AppUser;
import com.pcmc.bwg.entity.enums.Role;
import com.pcmc.bwg.entity.enums.UserStatus;
import com.pcmc.bwg.exception.BadRequestException;
import com.pcmc.bwg.exception.ConflictException;
import com.pcmc.bwg.exception.ResourceNotFoundException;
import com.pcmc.bwg.repository.AgencyRepository;
import com.pcmc.bwg.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final AppUserRepository appUserRepository;
    private final AgencyRepository agencyRepository;
    private final AadhaarService aadhaarService;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository appUserRepository, AgencyRepository agencyRepository,
                        AadhaarService aadhaarService, FileStorageService fileStorageService,
                        PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.agencyRepository = agencyRepository;
        this.aadhaarService = aadhaarService;
        this.fileStorageService = fileStorageService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AppUser create(Long agencyId, String fullName, String mobileNo, String email, String aadhaarNo,
                           String address, String pinCode, String password, MultipartFile photo) {
        log.info("Creating user for agencyId: {}", agencyId);

        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id " + agencyId));

        if (appUserRepository.existsByMobileNo(mobileNo)) {
            log.warn("User creation rejected, duplicate mobileNo for agencyId: {}", agencyId);
            throw new ConflictException("A user with this mobileNo already exists");
        }
        if (appUserRepository.existsByEmail(email)) {
            log.warn("User creation rejected, duplicate email for agencyId: {}", agencyId);
            throw new ConflictException("A user with this email already exists");
        }

        String aadhaarHash = aadhaarService.hash(aadhaarNo);
        if (appUserRepository.existsByAadhaarNoHash(aadhaarHash)) {
            log.warn("User creation rejected, duplicate Aadhaar number for agencyId: {}", agencyId);
            throw new ConflictException("A user with this Aadhaar number already exists");
        }

        AppUser user = new AppUser();
        user.setAgency(agency);
        user.setFullName(fullName);
        user.setMobileNo(mobileNo);
        user.setEmail(email);
        user.setAadhaarNoEncrypted(aadhaarService.encrypt(aadhaarNo));
        user.setAadhaarNoHash(aadhaarHash);
        user.setAddress(address);
        user.setPinCode(pinCode);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(Role.SURVEY_OFFICER);
        user.setStatus(UserStatus.INACTIVE);
        user.setMobileVerified(false);
        user.setAadhaarVerified(false);
        user.setPhotoPath(fileStorageService.store(photo));

        user = appUserRepository.save(user);

        log.info("User created successfully, userId: {}", user.getId());

        return user;
    }

    public List<AppUser> findAll(Long agencyId) {
        if (agencyId != null) {
            return appUserRepository.findByAgencyIdFetchAgency(agencyId);
        }
        return appUserRepository.findAllFetchAgency();
    }

    public AppUser findById(Long id) {
        return appUserRepository.findByIdFetchAgency(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @Transactional
    public AppUser update(Long id, UserUpdateRequest request) {
        AppUser user = findById(id);
        Agency agency = agencyRepository.findById(request.getAgencyId())
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id " + request.getAgencyId()));

        if (!user.getEmail().equals(request.getEmail()) && appUserRepository.existsByEmail(request.getEmail())) {
            log.warn("User update rejected, duplicate email for userId: {}", id);
            throw new ConflictException("A user with this email already exists");
        }

        user.setAgency(agency);
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setPinCode(request.getPinCode());
        user = appUserRepository.save(user);

        log.info("User updated successfully, userId: {}", user.getId());

        return user;
    }

    @Transactional
    public AppUser updateStatus(Long id, UserStatus status) {
        AppUser user = findById(id);
        if (status == UserStatus.ACTIVE && !(user.isMobileVerified() && user.isAadhaarVerified())) {
            log.warn("User activation rejected, verification incomplete for userId: {}", id);
            throw new BadRequestException("User must complete mobile and Aadhaar verification before activation");
        }
        user.setStatus(status);
        user = appUserRepository.save(user);

        log.info("User status updated, userId: {}, status: {}", user.getId(), status);

        return user;
    }

    @Transactional
    public AppUser updatePhoto(Long id, MultipartFile photo) {
        AppUser user = findById(id);
        user.setPhotoPath(fileStorageService.store(photo));
        user = appUserRepository.save(user);

        log.info("User photo updated, userId: {}", user.getId());

        return user;
    }

    @Transactional
    public void delete(Long id) {
        AppUser user = findById(id);
        appUserRepository.delete(user);

        log.info("User deleted, userId: {}", id);
    }

    @Transactional
    public void verifyAadhaar(Long id, String aadhaarNo) {
        AppUser user = findById(id);
        if (user.isAadhaarVerified()) {
            log.warn("Aadhaar verification rejected, already verified for userId: {}", id);
            throw new ConflictException("Aadhaar is already verified");
        }
        String hash = aadhaarService.hash(aadhaarNo);
        if (!hash.equals(user.getAadhaarNoHash())) {
            log.warn("Aadhaar verification failed, number mismatch for userId: {}", id);
            throw new BadRequestException("Aadhaar number does not match our records");
        }
        user.setAadhaarVerified(true);
        appUserRepository.save(user);

        log.info("Aadhaar verified successfully for userId: {}", id);
    }
}
