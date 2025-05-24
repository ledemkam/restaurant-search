package com.ledemkam.backend.mappers;

import com.ledemkam.backend.domain.dtos.GeoPointDto;
import com.ledemkam.backend.domain.dtos.RestaurantCreateUpdateRequestDto;
import com.ledemkam.backend.domain.dtos.RestaurantDto;
import com.ledemkam.backend.domain.entities.Restaurant;
import com.ledemkam.backend.domain.request.RestaurantCreateUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    RestaurantCreateUpdateRequest toRestaurantCreateUpdateRequest(RestaurantCreateUpdateRequestDto dto);

    RestaurantDto toRestaurantDto(Restaurant restaurant);

    @Mapping(target = "latitude", expression = "java(geoPoint.getLat())")
    @Mapping(target = "longitude", expression = "java(geoPoint.getLon())")
    GeoPointDto toGeoPointDto(GeoPoint geoPoint);
}