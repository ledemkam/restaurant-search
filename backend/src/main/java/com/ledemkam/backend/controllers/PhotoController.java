package com.ledemkam.backend.controllers;

import com.ledemkam.backend.domain.dtos.PhotoDto;
import com.ledemkam.backend.domain.entities.Photo;
import com.ledemkam.backend.mappers.PhotoMapper;
import com.ledemkam.backend.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
