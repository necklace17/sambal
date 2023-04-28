package com.necklace.accommodationservice.cataloginfo;

import com.necklace.accommodationservice.cataloginfo.domain.Accommodation;
import com.necklace.accommodationservice.cataloginfo.ports.in.CrudAccommodation;
import com.necklace.accommodationservice.cataloginfo.ports.out.AccommodationPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccommodationService implements CrudAccommodation {

  private final AccommodationPort accommodationPort;

  public AccommodationService(AccommodationPort accommodationPort) {
    this.accommodationPort = accommodationPort;
  }

  @Override
  public Mono<Accommodation> createAccommodation(Accommodation accommodation) {
    return accommodationPort.addAccommodation(accommodation);
  }

  @Override
  public Flux<Accommodation> getAllAccommodations() {
    return accommodationPort.getAllAccommodations();
  }

  @Override
  public Mono<Accommodation> getAccommodationById(String id) {
    return accommodationPort.getAccommodationById(id);
  }

  @Override
  public Mono<Accommodation> updateAccommodationById(String id, Accommodation accommodation) {
    return accommodationPort.updateAccommodationById(id, accommodation);
  }

  @Override
  public Mono<Void> deleteAccommodationById(String id) {
    return accommodationPort.deleteAccommodationById(id);
  }
}
