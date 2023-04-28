package com.necklace.accommodationservice.adapters.persistance;

import com.necklace.accommodationservice.adapters.persistance.entity.AccommodationEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccommodationRepository extends ReactiveMongoRepository<AccommodationEntity, String> {

}
