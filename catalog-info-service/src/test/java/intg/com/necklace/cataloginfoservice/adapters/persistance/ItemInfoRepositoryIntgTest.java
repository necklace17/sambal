package com.necklace.cataloginfoservice.adapters.persistance;

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
class ItemInfoRepositoryIntgTest {

  @Autowired
  ItemInfoRepository itemInfoRepository;

  @BeforeEach
  void setUp() {
    var itemInfos = List.of(
        new ItemInfo(null, "firstItem", "firstCategory", List.of("First Tag", "Second Tag"),
            "First Description", 11.30),
        new ItemInfo(null, "secondItem", "secondCategory", List.of("Third Tag"),
            "second Description", 12.30),
        new ItemInfo(null, "thirdItem", "third Category", List.of(),
            "third Description", 13.30)
    );
    this.itemInfoRepository.saveAll(itemInfos)
        .blockLast();
  }

  @AfterEach
  void tearDown() {
    this.itemInfoRepository.deleteAll()
        .block();
  }

  @Test
  void findAll() {
    //given

    //when
    var itemInfosFlux = itemInfoRepository.findAll();

    //then
    StepVerifier.create(itemInfosFlux)
        .expectNextCount(3)
        .verifyComplete();
  }

}
