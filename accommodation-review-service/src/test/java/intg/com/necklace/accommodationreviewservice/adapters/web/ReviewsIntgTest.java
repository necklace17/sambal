package com.necklace.accommodationreviewservice.adapters.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.necklace.accommodationreviewservice.adapters.persistance.ReviewReactiveRepository;
import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import com.necklace.accommodationreviewservice.adapters.web.dto.IncomingReviewDto;
import com.necklace.accommodationreviewservice.adapters.web.dto.OutgoingReviewDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

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
        new ReviewEntity(null, "3", "bad", 1.0));

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
    var review = new IncomingReviewDto("1", "very good", 5.0);
    // when
    webTestClient.post()
        .uri(REVIEWS_URL)
        .bodyValue(review)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(OutgoingReviewDto.class)
        .consumeWith(reviewEntityExchangeResult -> {
          OutgoingReviewDto responseBody = reviewEntityExchangeResult.getResponseBody();
          assertThat(responseBody.getReviewId()).isNotNull();
          assertThat(responseBody.getComment()).isEqualTo(review.getComment());
        });
  }
}
