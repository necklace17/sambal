package com.necklace.cataloginfoservice.cataloginfo.ports.out;

import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
import reactor.core.publisher.Mono;

public interface ItemInfoPort {

  Mono<ItemInfo> addItemInfo(ItemInfo itemInfo);

}
