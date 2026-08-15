package com.pcmc.bwg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final FileStorageProperties fileStorageProperties;

    public WebConfig(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath();
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create upload directory: " + uploadDir, e);
        }
        // Directory must exist before calling toUri() so the trailing slash is included;
        // without it, Spring cannot resolve child resources under this location.
        String uploadPath = uploadDir.toUri().toString();
        registry.addResourceHandler(fileStorageProperties.getBaseUrl() + "/**")
                .addResourceLocations(uploadPath);
    }
}
