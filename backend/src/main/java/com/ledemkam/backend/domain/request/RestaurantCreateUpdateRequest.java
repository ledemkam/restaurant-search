package com.ledemkam.backend.domain.request;

import com.ledemkam.backend.domain.entities.Address;
import com.ledemkam.backend.domain.entities.OperatingHours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantCreateUpdateRequest {
    private String name;                    // Restaurant's name
    private String cuisineType;             // Type of cuisine served
    private String contactInformation;
    private Address address;                // Physical location
    private OperatingHours operatingHours;  // Opening hours
    private List<String> photoIds;
}
