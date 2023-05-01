package com.necklace.accommodationreviewservice.reviews.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Review {

  @NotNull(message = "review.accommodationId : must not be null")
  private String accommodationId;

  private String comment;
  @Min(value = 0L, message = "review.rating : please pass a non-negative value")
  @Max(value = 5L, message = "review.rating : please pass a value less than 5")
  private Double rating;

}
