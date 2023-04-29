package com.necklace.accommodationreviewservice.adapters.persistance;

import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import com.necklace.accommodationreviewservice.reviews.domain.Review;
import com.necklace.accommodationreviewservice.reviews.ports.out.ReviewPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
class ReviewAdapter implements ReviewPort {

  private final ReviewReactiveRepository reviewReactiveRepository;


  public ReviewAdapter(ReviewReactiveRepository reviewReactiveRepository) {
    this.reviewReactiveRepository = reviewReactiveRepository;
  }

  @Override
  public Mono<ReviewEntity> addReview(Review review) {
    return reviewReactiveRepository.save(ReviewEntity.fromDomain(review));
  }
}
