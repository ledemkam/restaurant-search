package com.ledemkam.backend.controllers;

import com.ledemkam.backend.domain.dtos.PhotoDto;
import com.ledemkam.backend.domain.entities.Photo;
import com.ledemkam.backend.mappers.PhotoMapper;
import com.ledemkam.backend.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RequestMapping(path = "/api/photos")
@RestController
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    @PostMapping
    public PhotoDto uploadPhoto(
            @RequestParam("file") MultipartFile file) {
        Photo savedPhoto = photoService.uploadPhoto(file);
        return photoMapper.toDto(savedPhoto);
    }

    @GetMapping("/{id:.+}") //The {id:.+} path variable pattern allows for file extensions in the ID
    public ResponseEntity<Resource> getPhoto(@PathVariable String id) {
        return photoService.getPhotoAsResource(id).map(photo ->
                ResponseEntity.ok()
                        .contentType(MediaTypeFactory
                                .getMediaType(photo)
                                .orElse(MediaType.APPLICATION_OCTET_STREAM))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                        .body(photo)
        ).orElse(ResponseEntity.notFound().build());
    }
}
