package com.necklace.accommodationreviewservice.adapters.web.handler;

import com.necklace.accommodationreviewservice.adapters.persistance.ReviewReactiveRepository;
import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import com.necklace.accommodationreviewservice.reviews.domain.Review;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ReviewHandler {

  private final ReviewReactiveRepository reviewReactiveRepository;

  public ReviewHandler(ReviewReactiveRepository reviewReactiveRepository) {
    this.reviewReactiveRepository = reviewReactiveRepository;
  }

  public Mono<ServerResponse> addReview(ServerRequest request) {
    return request.bodyToMono(Review.class)
        .map(ReviewEntity::fromDomain)
        .flatMap(reviewReactiveRepository::save)
        .map(ReviewEntity::toDomain)
        .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
  }
}
