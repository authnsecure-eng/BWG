package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.user.UserResponse;
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
        log.info("START create agencyId={} mobileNo={}", agencyId, mobileNo);
        try {
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
            user.setPhotoPath(fileStorageService.store(photo));

            user = appUserRepository.save(user);

            log.info("User created successfully, userId: {}", user.getId());
            log.info("SUCCESS create agencyId={} userId={}", agencyId, user.getId());

            return user;
        } catch (RuntimeException ex) {
            log.error("ERROR create agencyId={} mobileNo={} - {}", agencyId, mobileNo, ex.getMessage(), ex);
            throw ex;
        }
    }

    public UserResponse toResponse(AppUser user) {
        return UserResponse.from(user, maskedAadhaarNo(user));
    }

    private String maskedAadhaarNo(AppUser user) {
        String decrypted = aadhaarService.decrypt(user.getAadhaarNoEncrypted());
        if (decrypted == null || decrypted.length() < 4) {
            return decrypted;
        }
        return "XXXX XXXX " + decrypted.substring(decrypted.length() - 4);
    }

    public List<AppUser> findAll(Long agencyId) {
        log.info("START findAll agencyId={}", agencyId);
        try {
            List<AppUser> result = agencyId != null
                    ? appUserRepository.findByAgencyIdFetchAgency(agencyId)
                    : appUserRepository.findAllFetchAgency();
            log.info("SUCCESS findAll agencyId={} count={}", agencyId, result.size());
            return result;
        } catch (RuntimeException ex) {
            log.error("ERROR findAll agencyId={} - {}", agencyId, ex.getMessage(), ex);
            throw ex;
        }
    }

    public AppUser findById(Long id) {
        log.info("START findById id={}", id);
        try {
            AppUser user = appUserRepository.findByIdFetchAgency(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
            log.info("SUCCESS findById id={}", id);
            return user;
        } catch (RuntimeException ex) {
            log.error("ERROR findById id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public AppUser update(Long id, UserUpdateRequest request) {
        log.info("START update id={}", id);
        try {
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
            log.info("SUCCESS update id={}", id);

            return user;
        } catch (RuntimeException ex) {
            log.error("ERROR update id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public AppUser updateStatus(Long id, UserStatus status) {
        log.info("START updateStatus id={}", id);
        try {
            AppUser user = findById(id);
            if (status == UserStatus.ACTIVE && !user.isMobileVerified()) {
                log.warn("User activation rejected, verification incomplete for userId: {}", id);
                throw new BadRequestException("User must complete mobile verification before activation");
            }
            user.setStatus(status);
            user = appUserRepository.save(user);

            log.info("User status updated, userId: {}, status: {}", user.getId(), status);
            log.info("SUCCESS updateStatus id={}", id);

            return user;
        } catch (RuntimeException ex) {
            log.error("ERROR updateStatus id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public AppUser updatePhoto(Long id, MultipartFile photo) {
        log.info("START updatePhoto id={}", id);
        try {
            AppUser user = findById(id);
            user.setPhotoPath(fileStorageService.store(photo));
            user = appUserRepository.save(user);

            log.info("User photo updated, userId: {}", user.getId());
            log.info("SUCCESS updatePhoto id={}", id);

            return user;
        } catch (RuntimeException ex) {
            log.error("ERROR updatePhoto id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public void delete(Long id) {
        log.info("START delete id={}", id);
        try {
            AppUser user = findById(id);
            appUserRepository.delete(user);

            log.info("User deleted, userId: {}", id);
            log.info("SUCCESS delete id={}", id);
        } catch (RuntimeException ex) {
            log.error("ERROR delete id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

}
