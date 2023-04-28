package com.necklace.cataloginfoservice.cataloginfo.ports.in;

import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
import reactor.core.publisher.Mono;

public interface CreateItemInfo {

  Mono<ItemInfo> createItemInfo(ItemInfo itemInfo);
}
