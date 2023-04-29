package com.necklace.accommodationservice.adapters.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.necklace.accommodationservice.accommodation.domain.Accommodation;
import com.necklace.accommodationservice.adapters.persistance.AccommodationRepository;
import com.necklace.accommodationservice.adapters.persistance.entity.AccommodationEntity;
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
class AccommodationControllerIntgTest {


  private final static String ACCOMMODATIONS_URL = "/v1/accommodation";
  @Autowired
  AccommodationRepository accommodationRepository;
  @Autowired
  WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    // given
    var accommodations = List.of(
        new AccommodationEntity(null, "firstAccommodation", "firstCategory",
            List.of("First Tag", "Second Tag"), "First Description", 11.30),
        new AccommodationEntity(null, "secondAccommodation", "secondCategory",
            List.of("Third Tag"), "second Description", 12.30),
        new AccommodationEntity("abc", "thirdAccommodation", "third Category",
            List.of(), "third Description", 13.30)
    );
    accommodationRepository.saveAll(accommodations)
        .blockLast();
  }

  @AfterEach
  void tearDown() {
    accommodationRepository.deleteAll()
        .block();
  }

  @Test
  void addAccommodation() {
    // given
    var accommodation = new Accommodation("anotherAccommodation", "another Category", List.of(),
        "another Description", 13.30);
    // when
    webTestClient.post()
        .uri(ACCOMMODATIONS_URL)
        .bodyValue(accommodation)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Accommodation.class)
        .consumeWith(accommodationEntityExchangeResult -> {
          // then
          var savedAccommodation = accommodationEntityExchangeResult.getResponseBody();
          assertThat(savedAccommodation).isNotNull();
          assertThat(savedAccommodation.getName()).isEqualTo(accommodation.getName());
        });
  }

  @Test
  void getAllAccommodations() {
    // when
    webTestClient.get()
        .uri(ACCOMMODATIONS_URL)
        .exchange()
        // then
        .expectStatus()
        .is2xxSuccessful()
        .expectBodyList(Accommodation.class)
        .hasSize(3);
  }

  @Test
  void getAccommodationById_not_found() {
    // when
    var accommodationId = "not_available";
    webTestClient.get()
        .uri(ACCOMMODATIONS_URL + "/{id}", accommodationId)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void getAccommodationById() {
    // when
    var accommodationId = "abc";
    webTestClient.get()
        .uri(ACCOMMODATIONS_URL + "/{id}", accommodationId)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody()
        .jsonPath("$.name")
        .isEqualTo("thirdAccommodation");
  }

  @Test
  void updateAccommodation_invalid_id() {
    // given
    var accommodationId = "not_found";
    var newName = "New Name";
    var updatedAccommodation = new AccommodationEntity("abc", "thirdAccommodation",
        "third Category", List.of(), "third Description", 13.30);
    updatedAccommodation.setName(newName);
    // when
    webTestClient.put()
        .uri(ACCOMMODATIONS_URL + "/{id}", accommodationId)
        .bodyValue(updatedAccommodation)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void updateAccommodation() {
    // given
    var accommodationId = "abc";
    var newName = "New Name";
    var updatedAccommodation = this.accommodationRepository.findById(accommodationId)
        .block()
        .toDomain();
    updatedAccommodation.setName(newName);
    // when
    webTestClient.put()
        .uri(ACCOMMODATIONS_URL + "/{id}", accommodationId)
        .bodyValue(updatedAccommodation)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(Accommodation.class)
        // then
        .consumeWith(entityExchangeResult -> assertThat(entityExchangeResult.getResponseBody()
            .getName()).isEqualTo(updatedAccommodation.getName()));
  }

  @Test
  void deleteAccommodation() {
    // given
    var accommodationId = "abc";
    // when
    webTestClient.delete()
        .uri(ACCOMMODATIONS_URL + "/{id}", accommodationId)
        .exchange()
        .expectStatus()
        .isNoContent();
  }

}

