package com.ledemkam.backend.mappers;

import com.ledemkam.backend.domain.dtos.ReviewCreateUpdateRequestDto;
import com.ledemkam.backend.domain.dtos.ReviewDto;
import com.ledemkam.backend.domain.entities.Review;
import com.ledemkam.backend.domain.request.ReviewCreateUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
    ReviewCreateUpdateRequest toReviewCreateUpdateRequest(ReviewCreateUpdateRequestDto dto);
    ReviewDto toDto(Review review);
}
