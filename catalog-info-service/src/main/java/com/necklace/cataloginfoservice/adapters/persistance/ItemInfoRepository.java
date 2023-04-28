package com.necklace.cataloginfoservice.adapters.persistance;

import com.necklace.cataloginfoservice.adapters.persistance.entity.ItemInfoEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ItemInfoRepository extends ReactiveMongoRepository<ItemInfoEntity, String> {

}
