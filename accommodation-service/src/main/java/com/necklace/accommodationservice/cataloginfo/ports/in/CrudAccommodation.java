package com.necklace.accommodationservice.cataloginfo.ports.in;

import com.necklace.accommodationservice.cataloginfo.domain.Accommodation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudAccommodation {

  Mono<Accommodation> createAccommodation(Accommodation accommodation);

  Flux<Accommodation> getAllAccommodations();

  Mono<Accommodation> getAccommodationById(String id);

  Mono<Accommodation> updateAccommodationById(String id, Accommodation accommodation);

  Mono<Void> deleteAccommodationById(String id);
}
