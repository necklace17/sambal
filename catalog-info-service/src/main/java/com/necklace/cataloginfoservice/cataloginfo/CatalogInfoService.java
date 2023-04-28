package com.necklace.cataloginfoservice.cataloginfo;

import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
import com.necklace.cataloginfoservice.cataloginfo.ports.in.CrudItemInfo;
import com.necklace.cataloginfoservice.cataloginfo.ports.out.ItemInfoPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CatalogInfoService implements CrudItemInfo {

  private final ItemInfoPort itemInfoPort;

  public CatalogInfoService(ItemInfoPort itemInfoPort) {
    this.itemInfoPort = itemInfoPort;
  }

  @Override
  public Mono<ItemInfo> createItemInfo(ItemInfo itemInfo) {
    return itemInfoPort.addItemInfo(itemInfo);
  }

  @Override
  public Flux<ItemInfo> getAllItemInfos() {
    return itemInfoPort.getAllItemInfos();
  }

  @Override
  public Mono<ItemInfo> getItemInfoById(String id) {
    return itemInfoPort.getItemInfoById(id);
  }

  @Override
  public Mono<ItemInfo> updateItemInfoById(String id, ItemInfo itemInfo) {
    return itemInfoPort.updateItemInfoById(id, itemInfo);
  }
}
