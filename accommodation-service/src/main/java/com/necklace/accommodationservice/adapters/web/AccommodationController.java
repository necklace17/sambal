package com.necklace.accommodationservice.adapters.web;

import com.necklace.accommodationservice.accommodation.domain.Accommodation;
import com.necklace.accommodationservice.accommodation.ports.in.CrudAccommodation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/accommodation")
public class AccommodationController {

  private final CrudAccommodation crudAccommodation;

  public AccommodationController(CrudAccommodation crudAccommodation) {
    this.crudAccommodation = crudAccommodation;
  }


  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Accommodation> addAccommodation(@RequestBody @Valid Accommodation accommodation) {
    return crudAccommodation.createAccommodation(accommodation);
  }

  @GetMapping
  public Flux<Accommodation> getAllAccommodations(
      @RequestParam(value = "name", required = false) String name) {
    if (name != null) {
      return crudAccommodation.getAccommodationsByName(name);
    }
    return crudAccommodation.getAllAccommodations();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Accommodation>> getAccommodationsById(@PathVariable String id) {
    return crudAccommodation.getAccommodationById(id)
        .map(ResponseEntity.ok()::body)
        .switchIfEmpty(Mono.just(ResponseEntity.notFound()
            .build()));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Accommodation>> updateAccommodationById(@PathVariable String id,
      @RequestBody @Valid Accommodation accommodation) {
    return crudAccommodation.updateAccommodationById(id, accommodation)
        .map(ResponseEntity.ok()::body)
        .switchIfEmpty(Mono.just(ResponseEntity.notFound()
            .build()));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteAccommodationById(@PathVariable String id) {
    return crudAccommodation.deleteAccommodationById(id);
  }
}
