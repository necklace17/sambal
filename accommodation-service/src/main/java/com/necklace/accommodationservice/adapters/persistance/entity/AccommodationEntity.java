package com.necklace.accommodationservice.adapters.persistance.entity;

import com.necklace.accommodationservice.accommodation.domain.Accommodation;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class AccommodationEntity {

  @Id
  private String accommodationId;
  private String name;
  private String category;
  private List<String> tags;
  private String description;
  private Double price;

  public static AccommodationEntity fromDomain(Accommodation accommodation) {
    return new AccommodationEntity(null, accommodation.getName(), accommodation.getCategory(),
        accommodation.getTags(),
        accommodation.getDescription(), accommodation.getPrice());
  }

  public Accommodation toDomain() {
    return new Accommodation(getName(), getCategory(), getTags(), getDescription(), getPrice());
  }
}
