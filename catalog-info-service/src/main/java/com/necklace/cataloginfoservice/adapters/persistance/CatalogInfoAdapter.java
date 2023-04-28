package com.necklace.cataloginfoservice.adapters.persistance;

import com.necklace.cataloginfoservice.adapters.persistance.entity.ItemInfoEntity;
import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
import com.necklace.cataloginfoservice.cataloginfo.ports.out.ItemInfoPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CatalogInfoAdapter implements ItemInfoPort {

  private final ItemInfoRepository itemInfoRepository;

  public CatalogInfoAdapter(ItemInfoRepository itemInfoRepository) {
    this.itemInfoRepository = itemInfoRepository;
  }

  @Override
  public Mono<ItemInfo> addItemInfo(ItemInfo itemInfo) {
    return itemInfoRepository.save(ItemInfoEntity.fromDomain(itemInfo))
        .map(ItemInfoEntity::toDomain);
  }

  @Override
  public Flux<ItemInfo> getAllItemInfos() {
    return itemInfoRepository.findAll()
        .map(ItemInfoEntity::toDomain);
  }

  @Override
  public Mono<ItemInfo> getItemInfoById(String id) {
    return itemInfoRepository.findById(String.valueOf(id))
        .map(ItemInfoEntity::toDomain);
  }

  @Override
  public Mono<ItemInfo> updateItemInfoById(String id, ItemInfo itemInfo) {
    return itemInfoRepository.findById(id)
        .flatMap(itemInfoEntity -> {
          itemInfoEntity.setName(itemInfo.getName());
          itemInfoEntity.setCategory(itemInfo.getCategory());
          itemInfoEntity.setTags(itemInfo.getTags());
          itemInfoEntity.setDescription(itemInfo.getDescription());
          itemInfoEntity.setPrice(itemInfo.getPrice());
          return itemInfoRepository.save(itemInfoEntity)
              .map(ItemInfoEntity::toDomain);
        });
  }
}
