package com.pcmc.bwg.service;

import com.pcmc.bwg.config.AdminSeedProperties;
import com.pcmc.bwg.entity.Admin;
import com.pcmc.bwg.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    private final AdminSeedProperties adminSeedProperties;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(AdminSeedProperties adminSeedProperties, AdminRepository adminRepository,
                        PasswordEncoder passwordEncoder) {
        this.adminSeedProperties = adminSeedProperties;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        log.info("START run");
        try {
            if (!adminSeedProperties.isEnabled()) {
                log.info("SUCCESS run (seeding disabled)");
                return;
            }
            if (adminRepository.existsByUsername(adminSeedProperties.getUsername())) {
                log.info("SUCCESS run (admin already exists)");
                return;
            }

            Admin admin = new Admin();
            admin.setUsername(adminSeedProperties.getUsername());
            admin.setPasswordHash(passwordEncoder.encode(adminSeedProperties.getPassword()));
            admin.setFullName("Super Admin");
            admin.setActive(true);
            adminRepository.save(admin);

            log.info("Seeded default admin user '{}'", adminSeedProperties.getUsername());
            log.info("SUCCESS run");
        } catch (RuntimeException ex) {
            log.error("ERROR run - {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
