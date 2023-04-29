package com.necklace.accommodationreviewservice.adapters.web.handler;

import com.necklace.accommodationreviewservice.adapters.web.dto.IncomingReviewDto;
import com.necklace.accommodationreviewservice.adapters.web.dto.OutgoingReviewDto;
import com.necklace.accommodationreviewservice.reviews.ports.in.AddReview;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ReviewHandler {

  private final AddReview addReview;

  public ReviewHandler(AddReview addReview) {
    this.addReview = addReview;
  }

  public Mono<ServerResponse> addReview(ServerRequest request) {
    return request.bodyToMono(IncomingReviewDto.class)
        .map(IncomingReviewDto::toDomain)
        .flatMap(addReview::addReview)
        .map(OutgoingReviewDto::fromDomain)
        .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
//    return request.bodyToMono(Review.class)
//        .map(ReviewEntity::fromDomain)
//        .flatMap(reviewReactiveRepository::save)
//        .map(ReviewEntity::toDomain)
//        .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
  }
}
