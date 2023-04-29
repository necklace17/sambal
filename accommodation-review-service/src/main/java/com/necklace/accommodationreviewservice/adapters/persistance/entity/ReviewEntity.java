package com.necklace.accommodationreviewservice.adapters.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {

  @Id
  private String reviewId;
  private String AccommodationId;
  private String comment;
  private Double rating;

}
