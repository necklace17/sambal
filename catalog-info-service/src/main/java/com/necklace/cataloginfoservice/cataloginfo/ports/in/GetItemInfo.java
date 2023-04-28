package com.necklace.cataloginfoservice.cataloginfo.ports.in;

import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetItemInfo {

  Flux<ItemInfo> getAllItemInfos();

  Mono<ItemInfo> getItemInfoById(String id);
}
