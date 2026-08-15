package com.pcmc.bwg.service;

import com.pcmc.bwg.config.FileStorageProperties;
import com.pcmc.bwg.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);

    private final FileStorageProperties fileStorageProperties;

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String original = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
        String extension = original.contains(".") ? original.substring(original.lastIndexOf('.')) : "";
        String storedName = UUID.randomUUID() + extension;

        try {
            Path uploadDir = Path.of(fileStorageProperties.getUploadDir()).toAbsolutePath();
            Files.createDirectories(uploadDir);
            Path target = uploadDir.resolve(storedName);
            file.transferTo(target);
        } catch (IOException e) {
            log.error("Failed to store uploaded photo, storedName: {}", storedName, e);
            throw new BadRequestException("Failed to store uploaded photo: " + e.getMessage());
        }

        log.debug("Stored uploaded photo as: {}", storedName);

        return fileStorageProperties.getBaseUrl() + "/" + storedName;
    }
}
