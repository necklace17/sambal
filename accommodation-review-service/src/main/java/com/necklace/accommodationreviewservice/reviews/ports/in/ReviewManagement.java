package com.necklace.accommodationreviewservice.reviews.ports.in;

import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import com.necklace.accommodationreviewservice.reviews.domain.Review;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReviewManagement {

  Mono<ReviewEntity> addReview(Review review);

  Mono<ReviewEntity> getReviewById(String id);

  Flux<ReviewEntity> getReviewsByAccommodation(String accommodationId);

  Mono<Void> deleteReviewById(String id);

  Mono<ReviewEntity> updateReview(String id, Review review);
}
