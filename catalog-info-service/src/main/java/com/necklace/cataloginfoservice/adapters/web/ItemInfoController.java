package com.necklace.cataloginfoservice.adapters.web;

import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
import com.necklace.cataloginfoservice.cataloginfo.ports.in.CreateItemInfo;
import com.necklace.cataloginfoservice.cataloginfo.ports.in.GetItemInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class ItemInfoController {

  private final CreateItemInfo createItemInfo;
  private final GetItemInfo getItemInfo;

  public ItemInfoController(CreateItemInfo createItemInfo, GetItemInfo getItemInfo) {
    this.createItemInfo = createItemInfo;
    this.getItemInfo = getItemInfo;
  }


  @PostMapping("/iteminfos")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ItemInfo> addItemInfo(@RequestBody ItemInfo itemInfo) {
    return createItemInfo.createItemInfo(itemInfo);
  }

  @GetMapping("/iteminfos")
  public Flux<ItemInfo> getAllItemInfos() {
    return getItemInfo.getAllItemInfos();
  }

  @GetMapping("/iteminfos/{id}")
  public Mono<ItemInfo> getAllItemInfosById(@PathVariable String id) {
    return getItemInfo.getItemInfoById(id);
  }

}
