package com.necklace.cataloginfoservice.adapters.web;

import com.necklace.cataloginfoservice.cataloginfo.domain.ItemInfo;
import com.necklace.cataloginfoservice.cataloginfo.ports.in.CrudItemInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class ItemInfoController {

  private final CrudItemInfo crudItemInfo;

  public ItemInfoController(CrudItemInfo crudItemInfo) {
    this.crudItemInfo = crudItemInfo;
  }


  @PostMapping("/iteminfos")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ItemInfo> addItemInfo(@RequestBody ItemInfo itemInfo) {
    return crudItemInfo.createItemInfo(itemInfo);
  }

  @GetMapping("/iteminfos")
  public Flux<ItemInfo> getAllItemInfos() {
    return crudItemInfo.getAllItemInfos();
  }

  @GetMapping("/iteminfos/{id}")
  public Mono<ItemInfo> getItemInfosById(@PathVariable String id) {
    return crudItemInfo.getItemInfoById(id);
  }

  @PutMapping("/iteminfos/{id}")
  public Mono<ItemInfo> updateItemInfoById(@PathVariable String id,
      @RequestBody ItemInfo itemInfo) {
    return crudItemInfo.updateItemInfoById(id, itemInfo);
  }

  @DeleteMapping("/iteminfos/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteItemInfoById(@PathVariable String id) {
    return crudItemInfo.deleteItemById(id);
  }
}
