package com.necklace.accommodationreviewservice.adapters.persistance;

import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReviewReactiveRepository extends ReactiveMongoRepository<ReviewEntity, String> {

  Flux<ReviewEntity> getAllByAccommodationId(String accommodationId);

}
