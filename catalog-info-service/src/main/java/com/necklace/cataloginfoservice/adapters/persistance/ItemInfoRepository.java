package com.necklace.cataloginfoservice.adapters.persistance;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ItemInfoRepository extends ReactiveMongoRepository<ItemInfo, String> {

}
