package com.necklace.accommodationservice.cataloginfo.ports.out;

import com.necklace.accommodationservice.cataloginfo.domain.Accommodation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccommodationPort {

  Mono<Accommodation> addAccommodation(Accommodation accommodation);

  Flux<Accommodation> getAllAccommodations();

  Mono<Accommodation> getAccommodationById(String id);

  Mono<Accommodation> updateAccommodationById(String id, Accommodation accommodation);

  Mono<Void> deleteAccommodationById(String id);
}
