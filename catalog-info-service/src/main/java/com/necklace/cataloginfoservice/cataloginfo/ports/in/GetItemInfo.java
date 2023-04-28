package com.necklace.cataloginfoservice.cataloginfo.ports.in;

import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
import reactor.core.publisher.Flux;

public interface GetItemInfo {

  Flux<ItemInfo> getAllItemInfos();
}
