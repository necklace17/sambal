package com.necklace.accommodationreviewservice.reviews;

import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import com.necklace.accommodationreviewservice.reviews.domain.Review;
import com.necklace.accommodationreviewservice.reviews.ports.in.AddReview;
import com.necklace.accommodationreviewservice.reviews.ports.in.GetReviews;
import com.necklace.accommodationreviewservice.reviews.ports.out.ReviewPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
class ReviewService implements AddReview, GetReviews {

  private final ReviewPort reviewPort;

  public ReviewService(ReviewPort reviewPort) {
    this.reviewPort = reviewPort;
  }

  @Override
  public Mono<ReviewEntity> addReview(Review review) {
    return reviewPort.addReview(review);
  }

  @Override
  public Flux<ReviewEntity> getReviewsByAccommodation(String accommodationId) {
    return reviewPort.getReviewsByAccommodation(accommodationId);
  }
}
