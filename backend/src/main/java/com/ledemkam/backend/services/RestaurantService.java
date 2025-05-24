package com.ledemkam.backend.services;

import com.ledemkam.backend.domain.entities.Restaurant;
import com.ledemkam.backend.domain.request.RestaurantCreateUpdateRequest;

public interface RestaurantService {
    Restaurant createRestaurant(RestaurantCreateUpdateRequest restaurant);
}
