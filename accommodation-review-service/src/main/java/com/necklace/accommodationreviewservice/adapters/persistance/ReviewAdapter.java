package com.necklace.accommodationreviewservice.adapters.persistance;

import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import com.necklace.accommodationreviewservice.reviews.domain.Review;
import com.necklace.accommodationreviewservice.reviews.ports.out.ReviewPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
class ReviewAdapter implements ReviewPort {

  private final ReviewReactiveRepository reviewReactiveRepository;

  @Override
  public Mono<ReviewEntity> addReview(Review review) {
    return reviewReactiveRepository.save(ReviewEntity.fromDomain(review));
  }

  @Override
  public Flux<ReviewEntity> getReviewsByAccommodation(String accommodationId) {
    return reviewReactiveRepository.getAllByAccommodationId(accommodationId);
  }

  @Override
  public Mono<ReviewEntity> getReviewById(String id) {
    return reviewReactiveRepository.findById(id);
  }

  @Override
  public Mono<Void> deleteReviewById(String id) {
    return reviewReactiveRepository.deleteById(id);
  }

  @Override
  public Mono<ReviewEntity> updateReview(String id, Review review) {
    return reviewReactiveRepository.findById(id)
        .doOnNext(reviewEntity -> {
          reviewEntity.setComment(review.getComment());
          reviewEntity.setRating(review.getRating());
        })
        .flatMap(reviewReactiveRepository::save);
  }
}
