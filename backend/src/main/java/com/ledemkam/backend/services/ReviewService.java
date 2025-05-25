package com.ledemkam.backend.services;

import com.ledemkam.backend.domain.entities.Review;
import com.ledemkam.backend.domain.entities.User;
import com.ledemkam.backend.domain.request.ReviewCreateUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Review createReview(User author, String restaurantId,
                        ReviewCreateUpdateRequest review);
    Page<Review> listReviews(String restaurantId, Pageable pageable);
}
