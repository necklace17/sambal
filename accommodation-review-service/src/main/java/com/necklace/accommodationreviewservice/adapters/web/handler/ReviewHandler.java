package com.necklace.accommodationreviewservice.adapters.web.handler;

import com.necklace.accommodationreviewservice.adapters.web.handler.dto.IncomingReviewDto;
import com.necklace.accommodationreviewservice.adapters.web.handler.dto.OutgoingReviewDto;
import com.necklace.accommodationreviewservice.reviews.ports.in.AddReview;
import com.necklace.accommodationreviewservice.reviews.ports.in.GetReviews;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ReviewHandler {

  private final AddReview addReview;
  private final GetReviews getReviews;

  public ReviewHandler(AddReview addReview, GetReviews getReviews) {
    this.addReview = addReview;
    this.getReviews = getReviews;
  }

  public Mono<ServerResponse> addReview(ServerRequest request) {
    return request.bodyToMono(IncomingReviewDto.class)
        .map(IncomingReviewDto::toDomain)
        .flatMap(addReview::addReview)
        .map(OutgoingReviewDto::fromPersistenceEntity)
        .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
  }

  public Mono<ServerResponse> getReviews(ServerRequest request) {
    var accommodationId = request.queryParam("accommodationId");
    if (!accommodationId.isPresent() || accommodationId.get()
        .isBlank()) {
      return ServerResponse.unprocessableEntity()
          .contentType(MediaType.APPLICATION_JSON)
          .body(Mono.just("accommodationId missing"), String.class);
    }
    Flux<OutgoingReviewDto> reviewsFlux = getReviews.getReviewsByAccommodation(
            accommodationId.get())
        .map(OutgoingReviewDto::fromPersistenceEntity);
    return ServerResponse.ok()
        .body(reviewsFlux, OutgoingReviewDto.class);
  }
}
