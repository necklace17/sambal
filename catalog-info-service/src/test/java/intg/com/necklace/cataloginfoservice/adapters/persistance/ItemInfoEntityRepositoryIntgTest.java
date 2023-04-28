package com.necklace.cataloginfoservice.adapters.persistance;


import static org.assertj.core.api.Assertions.assertThat;

import com.necklace.cataloginfoservice.adapters.persistance.entity.ItemInfoEntity;
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
class ItemInfoEntityRepositoryIntgTest {

  @Autowired
  ItemInfoRepository itemInfoRepository;

  @BeforeEach
  void setUp() {
    var itemInfos = List.of(
        new ItemInfoEntity(null, "firstItem", "firstCategory", List.of("First Tag", "Second Tag"),
            "First Description", 11.30),
        new ItemInfoEntity(null, "secondItem", "secondCategory", List.of("Third Tag"),
            "second Description", 12.30),
        new ItemInfoEntity("abc", "thirdItem", "third Category", List.of(),
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
    //when
    var itemInfosFlux = itemInfoRepository.findAll();
    //then
    StepVerifier.create(itemInfosFlux)
        .expectNextCount(3)
        .verifyComplete();
  }

  @Test
  void findOne() {
    // when
    var itemInfoMono = itemInfoRepository.findById("abc")
        .log();
    //then
    StepVerifier.create(itemInfoMono)
        .assertNext(info -> assertThat(info.getName()).isEqualTo("thirdItem"))
        .verifyComplete();
  }

  @Test
  void saveItemInfo() {
    // given
    var itemInfo = new ItemInfoEntity(null, "firstItem", "firstCategory",
        List.of("First Tag", "Second Tag"),
        "First Description", 11.30);
    // when
    var itemInfoMono = itemInfoRepository.save(itemInfo);
    // then
    StepVerifier.create(itemInfoMono)
        .assertNext(info -> {
          assertThat(info.getName()).isEqualTo(itemInfo.getName());
          assertThat(info.getItemInfoId()).isNotNull();
        })
        .verifyComplete();
  }

  @Test
  void deleteItemInfo() {
    // when
    itemInfoRepository.deleteById("abc")
        .block();
    var itemInfos = itemInfoRepository.findAll();
    // then
    StepVerifier.create(itemInfos)
        .expectNextCount(2)
        .verifyComplete();

  }

  @Test
  void updateItemInfo() {
    // given
    var itemInfo = itemInfoRepository.findById("abc")
        .block();
    var newName = "New Name";
    itemInfo.setName(newName);
    // when
    var itemInfoMono = itemInfoRepository.save(itemInfo);
    // then
    StepVerifier.create(itemInfoMono)
        .assertNext(info -> assertThat(info.getName()).isEqualTo(newName))
        .verifyComplete();
  }
}
