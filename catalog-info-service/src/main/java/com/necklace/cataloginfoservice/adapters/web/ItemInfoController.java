package com.necklace.cataloginfoservice.adapters.web;

import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
import com.necklace.cataloginfoservice.cataloginfo.ports.in.CreateItemInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class ItemInfoController {

  CreateItemInfo createItemInfo;

  @PostMapping("/iteminfos")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ItemInfo> addItemInfo(@RequestBody ItemInfo itemInfo) {
    return createItemInfo.createItemInfo(itemInfo);
  }
}
