package com.necklace.accommodationservice.adapters.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.necklace.accommodationservice.cataloginfo.domain.Accommodation;
import com.necklace.accommodationservice.cataloginfo.ports.in.CrudAccommodation;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = AccommodationController.class)
@AutoConfigureWebTestClient
class AccommodationControllerTest {

  private final static String ACCOMMODATIONS_URL = "/v1/accommodation";
  @Autowired
  private WebTestClient webTestClient;
  @MockBean
  private CrudAccommodation crudAccommodationMock;

  @Test
  void getAllAccommodations() {
    var accommodations = List.of(
        new Accommodation("firstAccommodation", "firstCategory",
            List.of("First Tag", "Second Tag"), "First Description", 11.30),
        new Accommodation("secondAccommodation", "secondCategory",
            List.of("Third Tag"), "second Description", 12.30),
        new Accommodation("thirdAccommodation", "third Category",
            List.of(), "third Description", 13.30)
    );
    // when
    when(crudAccommodationMock.getAllAccommodations()).thenReturn(
        Flux.fromIterable(accommodations));
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
  void addAccommodation_validation() {
    // given
    var accommodation = new Accommodation("", "", List.of(),
        "another Description", -13.30);
    // when
    webTestClient.post()
        .uri(ACCOMMODATIONS_URL)
        .bodyValue(accommodation)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(String.class)
        // then
        .consumeWith(accommodationEntityExchangeResult -> assertThat(
            accommodationEntityExchangeResult.getResponseBody()).contains(
            "accommodation.name", "accommodation.category", "accommodation.year"));
  }

  @Test
  void addAccommodation() {
    // given
    var accommodation = new Accommodation("anotherAccommodation", "another Category", List.of(),
        "another Description", 13.30);

    when(crudAccommodationMock.createAccommodation(accommodation)).thenReturn(
        Mono.just(accommodation));
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
  void getAccommodationById() {
    // when
    var accommodationId = "abc";
    var accommodation = new Accommodation("anotherAccommodation", "another Category", List.of(),
        "another Description", 13.30);
    when(crudAccommodationMock.getAccommodationById(accommodationId)).thenReturn(
        Mono.just(accommodation));
    webTestClient.get()
        .uri(ACCOMMODATIONS_URL + "/{id}", accommodationId)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody()
        .jsonPath("$.name")
        .isEqualTo(accommodation.getName());
  }

  @Test
  void updateAccommodation() {
    // when
    var accommodationId = "abc";
    var accommodation = new Accommodation("anotherAccommodation", "another Category", List.of(),
        "another Description", 13.30);
    when(crudAccommodationMock.updateAccommodationById(accommodationId, accommodation)).thenReturn(
        Mono.just(accommodation));
    webTestClient.put()
        .uri(ACCOMMODATIONS_URL + "/{id}", accommodationId)
        .bodyValue(accommodation)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(Accommodation.class)
        // then
        .consumeWith(entityExchangeResult -> assertThat(entityExchangeResult.getResponseBody()
            .getName()).isEqualTo(accommodation.getName()));
  }

  @Test
  void deleteAccommodation() {
    // given
    var accommodationId = "abc";
    // when
    when(crudAccommodationMock.deleteAccommodationById(accommodationId)).thenReturn(Mono.empty());
    webTestClient.delete()
        .uri(ACCOMMODATIONS_URL + "/{id}", accommodationId)
        .exchange()
        .expectStatus()
        .isNoContent();
  }

}
