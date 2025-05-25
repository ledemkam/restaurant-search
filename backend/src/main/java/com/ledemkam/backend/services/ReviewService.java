package com.ledemkam.backend.services;

import com.ledemkam.backend.domain.entities.Review;
import com.ledemkam.backend.domain.entities.User;
import com.ledemkam.backend.domain.request.ReviewCreateUpdateRequest;

public interface ReviewService {
    Review createReview(User author, String restaurantId,
                        ReviewCreateUpdateRequest review);
}
