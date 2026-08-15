package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.auth.LoginResponse;
import com.pcmc.bwg.entity.Admin;
import com.pcmc.bwg.entity.AppUser;
import com.pcmc.bwg.entity.enums.UserStatus;
import com.pcmc.bwg.exception.UnauthorizedException;
import com.pcmc.bwg.repository.AdminRepository;
import com.pcmc.bwg.repository.AppUserRepository;
import com.pcmc.bwg.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final AdminRepository adminRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AdminRepository adminRepository, AppUserRepository appUserRepository,
                        PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.adminRepository = adminRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse loginAdmin(String username, String password) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Admin login failed, unknown username: {}", username);
                    return new UnauthorizedException("Invalid username or password");
                });

        if (!admin.isActive()) {
            log.warn("Admin login failed, account inactive for username: {}", username);
            throw new UnauthorizedException("Admin account is inactive");
        }
        if (!passwordEncoder.matches(password, admin.getPasswordHash())) {
            log.warn("Admin login failed, invalid password for username: {}", username);
            throw new UnauthorizedException("Invalid username or password");
        }

        String token = jwtService.generateToken(String.valueOf(admin.getId()),
                Map.of("role", "ADMIN", "username", admin.getUsername()));

        log.info("Admin login successful for username: {}, adminId: {}", admin.getUsername(), admin.getId());

        return new LoginResponse(token, jwtService.getExpirationMinutes(), admin.getId(), admin.getUsername(), "ADMIN");
    }

    public LoginResponse loginUser(String mobileNo, String password) {
        AppUser user = appUserRepository.findByMobileNo(mobileNo)
                .orElseThrow(() -> {
                    log.warn("User login failed, unknown mobile number");
                    return new UnauthorizedException("Invalid mobile number or password");
                });

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            log.warn("User login failed, invalid password for userId: {}", user.getId());
            throw new UnauthorizedException("Invalid mobile number or password");
        }
        if (!user.isMobileVerified() || !user.isAadhaarVerified()) {
            log.warn("User login failed, verification incomplete for userId: {}", user.getId());
            throw new UnauthorizedException("Account verification is not complete yet");
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            log.warn("User login failed, account not active for userId: {}", user.getId());
            throw new UnauthorizedException("Account is not active");
        }

        String token = jwtService.generateToken(String.valueOf(user.getId()),
                Map.of("role", "SURVEY_OFFICER", "mobileNo", user.getMobileNo()));

        log.info("User login successful for userId: {}", user.getId());

        return new LoginResponse(token, jwtService.getExpirationMinutes(), user.getId(), user.getFullName(), "SURVEY_OFFICER");
    }
}
