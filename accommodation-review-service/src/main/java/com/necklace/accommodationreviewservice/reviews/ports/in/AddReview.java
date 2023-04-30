package com.necklace.accommodationreviewservice.reviews.ports.in;

import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import com.necklace.accommodationreviewservice.reviews.domain.Review;
import reactor.core.publisher.Mono;

public interface AddReview {

  Mono<ReviewEntity> addReview(Review review);
}
