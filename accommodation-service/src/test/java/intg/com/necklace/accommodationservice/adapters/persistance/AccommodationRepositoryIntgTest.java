package com.necklace.accommodationservice.adapters.persistance;

import static org.assertj.core.api.Assertions.assertThat;

import com.necklace.accommodationservice.adapters.persistance.entity.AccommodationEntity;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@DataMongoTest
@ActiveProfiles("test")
class AccommodationRepositoryIntgTest {

  @Autowired
  AccommodationRepository accommodationRepository;

  @BeforeEach
  void setUp() {
    var accommodations = List.of(
        new AccommodationEntity(null, "firstItem", "firstCategory",
            List.of("First Tag", "Second Tag"),
            "First Description", 11.30),
        new AccommodationEntity(null, "secondItem", "secondCategory", List.of("Third Tag"),
            "second Description", 12.30),
        new AccommodationEntity("abc", "thirdItem", "third Category", List.of(),
            "third Description", 13.30)
    );
    this.accommodationRepository.saveAll(accommodations)
        .blockLast();
  }

  @AfterEach
  void tearDown() {
    this.accommodationRepository.deleteAll()
        .block();
  }

  @Test
  void findAll() {
    //when
    var accommodationsFlux = accommodationRepository.findAll();
    //then
    StepVerifier.create(accommodationsFlux)
        .expectNextCount(3)
        .verifyComplete();
  }

  @Test
  void findOne() {
    // when
    var accommodationMono = accommodationRepository.findById("abc")
        .log();
    //then
    StepVerifier.create(accommodationMono)
        .assertNext(info -> assertThat(info.getName()).isEqualTo("thirdItem"))
        .verifyComplete();
  }

  @Test
  void saveAccommodation() {
    // given
    var accommodation = new AccommodationEntity(null, "firstItem", "firstCategory",
        List.of("First Tag", "Second Tag"),
        "First Description", 11.30);
    // when
    var accommodationMono = accommodationRepository.save(accommodation);
    // then
    StepVerifier.create(accommodationMono)
        .assertNext(info -> {
          assertThat(info.getName()).isEqualTo(accommodation.getName());
          assertThat(info.getAccommodationId()).isNotNull();
        })
        .verifyComplete();
  }


  @Test
  void deleteAccommodation() {
    // when
    accommodationRepository.deleteById("abc")
        .block();
    var accommodations = accommodationRepository.findAll();
    // then
    StepVerifier.create(accommodations)
        .expectNextCount(2)
        .verifyComplete();

  }

  @Test
  void updateAccommodation() {
    // given
    var accommodation = accommodationRepository.findById("abc")
        .block();
    var newName = "New Name";
    accommodation.setName(newName);
    // when
    var accommodationMono = accommodationRepository.save(accommodation);
    // then
    StepVerifier.create(accommodationMono)
        .assertNext(info -> assertThat(info.getName()).isEqualTo(newName))
        .verifyComplete();
  }
}
