package com.ledemkam.backend.services;

import com.ledemkam.backend.domain.entities.Restaurant;
import com.ledemkam.backend.domain.request.RestaurantCreateUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestaurantService {
    Restaurant createRestaurant(RestaurantCreateUpdateRequest restaurant);
    Page<Restaurant> searchRestaurants(String query,
                                       Float minRating,
                                       Float latitude,
                                       Float longitude,
                                       Float radius,
                                       Pageable pageable);
    Optional<Restaurant> getRestaurant(String id);
    Restaurant updateRestaurant(String id, RestaurantCreateUpdateRequest restaurant);
    void deleteRestaurant(String id);
}

