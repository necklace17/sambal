package com.necklace.accommodationreviewservice.reviews.ports.in;

import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import reactor.core.publisher.Flux;

public interface GetReviews {

  Flux<ReviewEntity> getReviewsByAccommodation(String accommodationId);
}
