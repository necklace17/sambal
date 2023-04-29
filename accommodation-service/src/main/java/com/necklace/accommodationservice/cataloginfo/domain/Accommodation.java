package com.necklace.accommodationservice.cataloginfo.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Accommodation {

  @NotBlank(message = "accommodation.name must be present")
  private String name;
  @NotBlank(message = "accommodation.category must be present")
  private String category;
  private List<String> tags;
  private String description;
  @NotNull
  @Positive(message = "accommodation.year must be a positive value")
  private Double price;
}
