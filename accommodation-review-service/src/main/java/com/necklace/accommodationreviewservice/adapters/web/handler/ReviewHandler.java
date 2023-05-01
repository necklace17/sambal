package com.necklace.accommodationreviewservice.adapters.web.handler;

import com.necklace.accommodationreviewservice.adapters.web.handler.dto.IncomingReviewDto;
import com.necklace.accommodationreviewservice.adapters.web.handler.dto.OutgoingReviewDto;
import com.necklace.accommodationreviewservice.reviews.ports.in.ReviewManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReviewHandler {

  private final ReviewManagement reviewManagement;

  public Mono<ServerResponse> addReview(ServerRequest request) {
    return request.bodyToMono(IncomingReviewDto.class)
        .map(IncomingReviewDto::toDomain)
        .flatMap(reviewManagement::addReview)
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
    Flux<OutgoingReviewDto> reviewsFlux = reviewManagement.getReviewsByAccommodation(
            accommodationId.get())
        .map(OutgoingReviewDto::fromPersistenceEntity);
    return ServerResponse.ok()
        .body(reviewsFlux, OutgoingReviewDto.class);
  }

  public Mono<ServerResponse> getReviewById(ServerRequest request) {
    return reviewManagement.getReviewById(request.pathVariable("id"))
        .map(OutgoingReviewDto::fromPersistenceEntity)
        .flatMap(ServerResponse.ok()::bodyValue)
        .switchIfEmpty(ServerResponse.notFound()
            .build());
  }

  public Mono<ServerResponse> deleteReviewById(ServerRequest request) {
    reviewManagement.deleteReviewById(request.pathVariable("id"));
    return ServerResponse.noContent()
        .build();
  }
}
