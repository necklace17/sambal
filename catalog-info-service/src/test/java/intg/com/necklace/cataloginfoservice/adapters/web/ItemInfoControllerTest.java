package com.necklace.cataloginfoservice.adapters.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.necklace.cataloginfoservice.adapters.persistance.ItemInfoRepository;
import com.necklace.cataloginfoservice.adapters.persistance.entity.ItemInfoEntity;
import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
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
class ItemInfoControllerIntgTest {

  private static String ITEM_INFOS_URL = "/v1/iteminfos";
  @Autowired
  ItemInfoRepository itemInfoRepository;
  @Autowired
  WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    // given
    var itemInfos = List.of(
        new ItemInfoEntity(null, "firstItem", "firstCategory",
            List.of("First Tag", "Second Tag"), "First Description", 11.30),
        new ItemInfoEntity(null, "secondItem", "secondCategory",
            List.of("Third Tag"), "second Description", 12.30),
        new ItemInfoEntity("abc", "thirdItem", "third Category",
            List.of(), "third Description", 13.30)
    );
    itemInfoRepository.saveAll(itemInfos)
        .blockLast();
  }

  @AfterEach
  void tearDown() {
    itemInfoRepository.deleteAll()
        .block();
  }

  @Test
  void addItemInfo() {
    // given
    var itemInfo = new ItemInfo("anotherItem", "another Category", List.of(),
        "another Description", 13.30);
    // when
    webTestClient.post()
        .uri(ITEM_INFOS_URL)
        .bodyValue(itemInfo)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ItemInfo.class)
        .consumeWith(itemInfoEntityExchangeResult -> {
          // then
          var savedItemInfo = itemInfoEntityExchangeResult.getResponseBody();
          assertThat(savedItemInfo).isNotNull();
          assertThat(savedItemInfo.getName()).isEqualTo(itemInfo.getName());
        });
  }

  @Test
  void allItemInfos() {
    // when
    webTestClient.get()
        .uri(ITEM_INFOS_URL)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBodyList(ItemInfo.class)
        .hasSize(3);
  }

  @Test
  void getItemInfoById() {
    // when
    var itemInfoId = "abc";
    webTestClient.get()
        .uri(ITEM_INFOS_URL + "/{id}", itemInfoId)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody()
        .jsonPath("$.name")
        .isEqualTo("thirdItem");
  }

  @Test
  void updateItem() {
    // given
    var itemInfoId = "abc";
    var newName = "New Name";
    var updatedItem = this.itemInfoRepository.findById(itemInfoId)
        .block()
        .toDomain();
    updatedItem.setName(newName);
    // when
    webTestClient.put()
        .uri(ITEM_INFOS_URL + "/{id}", itemInfoId)
        .bodyValue(updatedItem)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(ItemInfo.class)
        // then
        .consumeWith(entityExchangeResult -> assertThat(entityExchangeResult.getResponseBody()
            .getName()).isEqualTo(updatedItem.getName()));
  }

  @Test
  void deleteItem() {
    // given
    var itemInfoId = "abc";
    // when
    webTestClient.delete()
        .uri(ITEM_INFOS_URL + "/{id}", itemInfoId)
        .exchange()
        .expectStatus()
        .isNoContent();
  }
}
