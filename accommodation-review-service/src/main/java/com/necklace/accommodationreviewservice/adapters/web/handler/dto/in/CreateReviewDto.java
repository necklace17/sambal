package com.necklace.accommodationreviewservice.adapters.web.handler.dto.in;

import com.necklace.accommodationreviewservice.reviews.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateReviewDto {

  private String accommodationId;
  private String comment;
  private Double rating;

  public Review toDomain() {
    return new Review(accommodationId, comment, rating);
  }
}
