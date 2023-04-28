package com.necklace.accommodationservice.adapters.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.necklace.accommodationservice.adapters.persistance.AccommodationRepository;
import com.necklace.accommodationservice.adapters.persistance.entity.AccommodationEntity;
import com.necklace.accommodationservice.cataloginfo.domain.Accommodation;
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


  private static String ACCOMMODATIONS_URL = "/v1/accommodation";
  @Autowired
  AccommodationRepository accommodationRepository;
  @Autowired
  WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    // given
    var Accommodations = List.of(
        new AccommodationEntity(null, "firstAccommodation", "firstCategory",
            List.of("First Tag", "Second Tag"), "First Description", 11.30),
        new AccommodationEntity(null, "secondAccommodation", "secondCategory",
            List.of("Third Tag"), "second Description", 12.30),
        new AccommodationEntity("abc", "thirdAccommodation", "third Category",
            List.of(), "third Description", 13.30)
    );
    accommodationRepository.saveAll(Accommodations)
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
    var Accommodation = new Accommodation("anotherAccommodation", "another Category", List.of(),
        "another Description", 13.30);
    // when
    webTestClient.post()
        .uri(ACCOMMODATIONS_URL)
        .bodyValue(Accommodation)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Accommodation.class)
        .consumeWith(AccommodationEntityExchangeResult -> {
          // then
          var savedAccommodation = AccommodationEntityExchangeResult.getResponseBody();
          assertThat(savedAccommodation).isNotNull();
          assertThat(savedAccommodation.getName()).isEqualTo(Accommodation.getName());
        });
  }

  @Test
  void allAccommodations() {
    // when
    webTestClient.get()
        .uri(ACCOMMODATIONS_URL)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBodyList(Accommodation.class)
        .hasSize(3);
  }

  @Test
  void getAccommodationById() {
    // when
    var AccommodationId = "abc";
    webTestClient.get()
        .uri(ACCOMMODATIONS_URL + "/{id}", AccommodationId)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody()
        .jsonPath("$.name")
        .isEqualTo("thirdAccommodation");
  }

  @Test
  void updateAccommodation() {
    // given
    var AccommodationId = "abc";
    var newName = "New Name";
    var updatedAccommodation = this.accommodationRepository.findById(AccommodationId)
        .block()
        .toDomain();
    updatedAccommodation.setName(newName);
    // when
    webTestClient.put()
        .uri(ACCOMMODATIONS_URL + "/{id}", AccommodationId)
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
    var AccommodationId = "abc";
    // when
    webTestClient.delete()
        .uri(ACCOMMODATIONS_URL + "/{id}", AccommodationId)
        .exchange()
        .expectStatus()
        .isNoContent();
  }
}

