package com.necklace.accommodationreviewservice.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import com.necklace.accommodationreviewservice.adapters.persistance.ReviewReactiveRepository;
import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import com.necklace.accommodationreviewservice.adapters.web.handler.dto.in.CreateReviewDto;
import com.necklace.accommodationreviewservice.adapters.web.handler.dto.in.UpdateReviewDto;
import com.necklace.accommodationreviewservice.adapters.web.handler.dto.out.OutgoingReviewDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class ReviewsIntgTest {

  private final String REVIEWS_URL = "/v1/reviews";
  @Autowired
  WebTestClient webTestClient;

  @Autowired
  ReviewReactiveRepository reviewReactiveRepository;

  @BeforeEach
  void setUp() {
    var reviews = List.of(
        new ReviewEntity(null, "1", "very good", 5.0),
        new ReviewEntity(null, "2", "good", 4.0),
        new ReviewEntity("abc", "3", "bad", 1.0));

    reviewReactiveRepository.saveAll(reviews)
        .blockLast();
  }

  @AfterEach
  void tearDown() {
    reviewReactiveRepository.deleteAll()
        .block();
  }

  @Test
  void addReview() {
    // given
    var review = new CreateReviewDto("1", "very good", 5.0);
    // when
    webTestClient.post()
        .uri(REVIEWS_URL)
        .bodyValue(review)
        .exchange()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectStatus()
        .isCreated()
        .expectBody(OutgoingReviewDto.class)
        .consumeWith(reviewEntityExchangeResult -> {
          OutgoingReviewDto responseBody = reviewEntityExchangeResult.getResponseBody();
          assertThat(responseBody.getReviewId()).isNotNull();
          assertThat(responseBody.getComment()).isEqualTo(review.getComment());
        });
  }

  @Test
  void getReviewById_not_found() {
    var reviewId = "not_found";
    // when
    webTestClient.get()
        .uri(REVIEWS_URL + "/{id}", reviewId)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void getReviewById() {
    var reviewId = "abc";
    // when
    webTestClient.get()
        .uri(REVIEWS_URL + "/{id}", reviewId)
        .exchange()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(OutgoingReviewDto.class)
        .consumeWith(reviewEntityExchangeResult -> {
          var responseBody = reviewEntityExchangeResult.getResponseBody();
          assertThat(responseBody.getReviewId()).isNotNull();
          assertThat(responseBody.getComment()
              .equals("bad"));
        });
  }

  @Test
  void deleteReviewById() {
    var reviewId = "abc";
    // when
    webTestClient.delete()
        .uri(REVIEWS_URL + "/{id}", reviewId)
        .exchange()
        .expectStatus()
        .isNoContent();
  }


  @Test
  void getReviewByAccommodation() {
    var accommodationId = "3";
    var uri = UriComponentsBuilder.fromUriString(REVIEWS_URL)
        .queryParam("accommodationId", accommodationId)
        .buildAndExpand()
        .toUri();
    // when
    webTestClient.get()
        .uri(uri)
        .exchange()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectStatus()
        .is2xxSuccessful()
        .expectBodyList(OutgoingReviewDto.class)
        .hasSize(1)
        .consumeWith(
            reviewEntityExchangeResult -> assertThat(reviewEntityExchangeResult.getResponseBody())
                .extracting(OutgoingReviewDto::getComment)
                .contains("bad")
                .doesNotContain("good"));
  }

  @Test
  void getReviewByAccommodation_missing_accommodation_id() {
    // when
    webTestClient.get()
        .uri(REVIEWS_URL)
        .exchange()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectStatus()
        .is4xxClientError()
        .expectBody(String.class)
        .consumeWith(stringEntityExchangeResult -> assertThat(
            stringEntityExchangeResult.getResponseBody()).contains("missing")
            .contains("accommodationId"));
  }

  @Test
  void getReviewByAccommodation_blank_accommodation_id() {
    var accommodationId = "";
    var uri = UriComponentsBuilder.fromUriString(REVIEWS_URL)
        .queryParam("accommodationId", accommodationId)
        .buildAndExpand()
        .toUri();
    // when
    webTestClient.get()
        .uri(uri)
        .exchange()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectStatus()
        .is4xxClientError()
        .expectBody(String.class)
        .consumeWith(stringEntityExchangeResult -> assertThat(
            stringEntityExchangeResult.getResponseBody()).contains("missing")
            .contains("accommodationId"));
  }

  @Test
  void getReviewByAccommodation_not_present() {
    var accommodationId = "not_there";
    var uri = UriComponentsBuilder.fromUriString(REVIEWS_URL)
        .queryParam("accommodationId", accommodationId)
        .buildAndExpand()
        .toUri();
    // when
    webTestClient.get()
        .uri(uri)
        .exchange()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectStatus()
        .is2xxSuccessful()
        .expectBodyList(OutgoingReviewDto.class)
        .hasSize(0);
  }

  @Test
  void updateReview() {
    var reviewId = "abc";
    var newComment = "it was good";
    var newRating = 5.0;
    var updatedReview = new UpdateReviewDto(newComment, newRating);

    webTestClient.put()
        .uri(REVIEWS_URL + "/{id}", reviewId)
        .bodyValue(updatedReview)
        .exchange()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(OutgoingReviewDto.class)
        .consumeWith(reviewEntityExchangeResult -> {
          assertThat(reviewEntityExchangeResult.getResponseBody()).extracting(
                  OutgoingReviewDto::getReviewId, OutgoingReviewDto::getAccommodationId,
                  OutgoingReviewDto::getComment, OutgoingReviewDto::getRating)
              .contains(reviewId, "3", updatedReview.getComment(), updatedReview.getRating());
        });
    ReviewEntity entityFromDb = reviewReactiveRepository.findById(reviewId)
        .block();
    assertThat(entityFromDb).extracting(
            ReviewEntity::getReviewId, ReviewEntity::getAccommodationId,
            ReviewEntity::getComment, ReviewEntity::getRating)
        .contains(reviewId, "3", updatedReview.getComment(), updatedReview.getRating());
  }
}
