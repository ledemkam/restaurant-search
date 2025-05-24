package com.ledemkam.backend.services;

import com.ledemkam.backend.domain.entities.Address;
import com.ledemkam.backend.domain.entities.GeoLocation;

public interface GeoLocationService {
    GeoLocation geoLocate(Address address);
}
