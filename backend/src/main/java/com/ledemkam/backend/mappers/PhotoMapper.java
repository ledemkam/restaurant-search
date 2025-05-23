package com.ledemkam.backend.mappers;

import com.ledemkam.backend.domain.dtos.PhotoDto;
import com.ledemkam.backend.domain.entities.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {
    PhotoDto toDto(Photo photo);
}
