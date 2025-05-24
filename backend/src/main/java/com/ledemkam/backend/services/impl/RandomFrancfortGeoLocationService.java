package com.ledemkam.backend.services.impl;

import com.ledemkam.backend.domain.entities.Address;
import com.ledemkam.backend.domain.entities.GeoLocation;
import com.ledemkam.backend.services.GeoLocationService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomFrancfortGeoLocationService implements GeoLocationService {

    private static final float MIN_LATITUDE = 50.05f;
    private static final float MAX_LATITUDE = 50.20f;
    private static final float MIN_LONGITUDE = 8.55f;
    private static final float MAX_LONGITUDE = 8.80f;

    @Override
    public GeoLocation getGeoLocation(Address address) {
        Random random = new Random();
        double latitude = MIN_LATITUDE + random.nextDouble() * (MAX_LATITUDE - MIN_LATITUDE);
        double longitude = MIN_LONGITUDE + random.nextDouble() * (MAX_LONGITUDE - MIN_LONGITUDE);

        return GeoLocation.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
