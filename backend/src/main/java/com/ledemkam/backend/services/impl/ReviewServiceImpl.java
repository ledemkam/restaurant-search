package com.ledemkam.backend.services.impl;

import com.ledemkam.backend.domain.entities.Photo;
import com.ledemkam.backend.domain.entities.Restaurant;
import com.ledemkam.backend.domain.entities.Review;
import com.ledemkam.backend.domain.entities.User;
import com.ledemkam.backend.domain.request.ReviewCreateUpdateRequest;
import com.ledemkam.backend.exceptions.RestaurantNotFoundException;
import com.ledemkam.backend.exceptions.ReviewNotAllowedException;
import com.ledemkam.backend.repository.RestaurantRepository;
import com.ledemkam.backend.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest createReview) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);

        // Check if user has already reviewed this restaurant
        boolean hasExistingReview = restaurant.getReviews().stream()
                .anyMatch(r -> r.getWrittenBy().getId().equals(author.getId()));

        if (hasExistingReview) {
            throw new ReviewNotAllowedException("User has already reviewed this restaurant");
        }

        LocalDateTime now = LocalDateTime.now();
        // Create photos
        List<Photo> photos = createReview.getPhotoIds().stream().map(url -> {
            Photo photo = new Photo();
            photo.setUrl(url);
            photo.setUploadDate(now);
            return photo;
        }).collect(Collectors.toList());

        // Create review
        Review review = Review.builder()
                .id(UUID.randomUUID().toString())
                .content(createReview.getContent())
                .rating(createReview.getRating())
                .photos(photos)
                .datePosted(now)
                .lastEdited(now)
                .writtenBy(author)
                .build();

        // Add review to restaurant
        restaurant.getReviews().add(review);

        // Update restaurant's average rating
        updateRestaurantAverageRating(restaurant);

        // Save restaurant with new review
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        // Return the newly created review
        return updatedRestaurant.getReviews().stream()
                .filter(r -> r.getDatePosted().equals(review.getDatePosted()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error retrieving created review"));
    }

    private Restaurant getRestaurantOrThrow(String restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId));
    }

    private void updateRestaurantAverageRating(Restaurant restaurant) {
        List<Review> reviews = restaurant.getReviews();
        if (reviews.isEmpty()) {
            restaurant.setAverageRating(0.0f);
        } else {
            float averageRating = (float) reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);
            restaurant.setAverageRating(averageRating);
        }
    }
}
