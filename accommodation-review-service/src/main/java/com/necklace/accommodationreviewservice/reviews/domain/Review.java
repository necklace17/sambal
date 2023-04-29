package com.necklace.accommodationreviewservice.reviews.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Review {

  private String AccommodationId;
  private String comment;
  private Double rating;

}
