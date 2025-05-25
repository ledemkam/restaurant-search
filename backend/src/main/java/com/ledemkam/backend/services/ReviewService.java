package com.ledemkam.backend.services;

import com.ledemkam.backend.domain.entities.Review;
import com.ledemkam.backend.domain.entities.User;
import com.ledemkam.backend.domain.request.ReviewCreateUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewService {
    Review createReview(User author, String restaurantId,
                        ReviewCreateUpdateRequest review);
    Page<Review> listReviews(String restaurantId, Pageable pageable);
    Optional<Review> getRestaurantReview(String restaurantId, String reviewId);
}
