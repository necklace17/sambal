package com.necklace.accommodationservice.adapters.persistance;

import com.necklace.accommodationservice.adapters.persistance.entity.AccommodationEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AccommodationRepository extends
    ReactiveMongoRepository<AccommodationEntity, String> {

  Flux<AccommodationEntity> findByName(String name);

}
