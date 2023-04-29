package com.necklace.accommodationreviewservice.reviews;

import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import com.necklace.accommodationreviewservice.reviews.domain.Review;
import com.necklace.accommodationreviewservice.reviews.ports.in.AddReview;
import com.necklace.accommodationreviewservice.reviews.ports.out.ReviewPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReviewService implements AddReview {

  private final ReviewPort reviewPort;

  public ReviewService(ReviewPort reviewPort) {
    this.reviewPort = reviewPort;
  }

  @Override
  public Mono<ReviewEntity> addReview(Review review) {
    return reviewPort.addReview(review);
  }
}
