package com.necklace.cataloginfoservice.cataloginfo;

import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
import com.necklace.cataloginfoservice.cataloginfo.ports.in.CreateItemInfo;
import com.necklace.cataloginfoservice.cataloginfo.ports.in.GetItemInfo;
import com.necklace.cataloginfoservice.cataloginfo.ports.out.ItemInfoPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CatalogInfoService implements CreateItemInfo, GetItemInfo {

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
}
