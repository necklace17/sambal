package com.necklace.accommodationservice.adapters.persistance;

import com.necklace.accommodationservice.accommodation.domain.Accommodation;
import com.necklace.accommodationservice.accommodation.ports.out.AccommodationPort;
import com.necklace.accommodationservice.adapters.persistance.entity.AccommodationEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CatalogInfoAdapter implements AccommodationPort {

  private final AccommodationRepository accommodationRepository;

  public CatalogInfoAdapter(AccommodationRepository accommodationRepository) {
    this.accommodationRepository = accommodationRepository;
  }

  @Override
  public Mono<Accommodation> addAccommodation(Accommodation accommodation) {
    return accommodationRepository.save(AccommodationEntity.fromDomain(accommodation))
        .map(AccommodationEntity::toDomain);
  }

  @Override
  public Flux<Accommodation> getAllAccommodations() {
    return accommodationRepository.findAll()
        .map(AccommodationEntity::toDomain);
  }

  @Override
  public Mono<Accommodation> getAccommodationById(String id) {
    return accommodationRepository.findById(String.valueOf(id))
        .map(AccommodationEntity::toDomain);
  }

  @Override
  public Mono<Accommodation> updateAccommodationById(String id, Accommodation accommodation) {
    return accommodationRepository.findById(id)
        .flatMap(accommodationEntity -> {
          accommodationEntity.setName(accommodation.getName());
          accommodationEntity.setCategory(accommodation.getCategory());
          accommodationEntity.setTags(accommodation.getTags());
          accommodationEntity.setDescription(accommodation.getDescription());
          accommodationEntity.setPrice(accommodation.getPrice());
          return accommodationRepository.save(accommodationEntity)
              .map(AccommodationEntity::toDomain);
        });
  }

  @Override
  public Mono<Void> deleteAccommodationById(String id) {
    return accommodationRepository.deleteById(id);
  }

  @Override
  public Flux<Accommodation> getAllAccommodationsByName(String name) {
    return accommodationRepository.findByName(name)
        .map(AccommodationEntity::toDomain);
  }
}
