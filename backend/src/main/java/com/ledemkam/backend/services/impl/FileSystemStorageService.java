package com.ledemkam.backend.services.impl;

import com.ledemkam.backend.entities.Photo;
import com.ledemkam.backend.exceptions.StorageException;
import com.ledemkam.backend.services.PhotoService;
import com.ledemkam.backend.services.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class FileSystemStorageService implements StorageService {
    @Value("${app.storage.location:uploads}")
    private String storageLocation;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        rootLocation = Paths.get(storageLocation);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file, String filename) {
        try {
            // Check for empty files
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file");
            }

            // Create final filename with extension
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String finalFilename = filename + "." + extension;

            // Resolve and normalize the destination path
            Path destinationFile = this.rootLocation.resolve(Paths.get(finalFilename))
                    .normalize().toAbsolutePath();

            // Security check to prevent directory traversal
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory");
            }

            // Copy the file to the destination
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return finalFilename;
        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    @Override
    public Optional<Resource> loadAsResource(String filename) {
        try {
            // Resolve the file path relative to our root location
            Path file = rootLocation.resolve(filename);

            // Create a Resource object from the file path
            Resource resource = new UrlResource(file.toUri());

            // Check if the resource exists and is readable
            if (resource.exists() || resource.isReadable()) {
                return Optional.of(resource);
            } else {
                return Optional.empty();
            }
        } catch (MalformedURLException e) {
            log.debug("Could not read file: " + filename, e);
            return Optional.empty();
        }
    }

    @Service
    @RequiredArgsConstructor
    public static class PhotoServiceImpl implements PhotoService {
        private final StorageService storageService;

        @Override
        public Photo uploadPhoto(MultipartFile file) {
            // Generate a unique ID for the photo
            String photoId = UUID.randomUUID().toString();

            // Store the file and get its URL
            String url = storageService.store(file, photoId);

            // Create and populate the photo entity
            Photo photo = new Photo();
            photo.setUrl(url);
            photo.setUploadDate(LocalDateTime.now());

            return photo;
        }

        @Override
       public Optional<Resource> getPhotoAsResource(String id) {
            return storageService.loadAsResource(id);
        }
    }
}
