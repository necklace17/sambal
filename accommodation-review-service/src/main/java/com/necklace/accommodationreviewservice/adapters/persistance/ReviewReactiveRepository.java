package com.necklace.accommodationreviewservice.adapters.persistance;

import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReviewReactiveRepository extends ReactiveMongoRepository<ReviewEntity, String> {

}
