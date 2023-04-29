package com.necklace.accommodationreviewservice.adapters.web.handler.dto;

import com.necklace.accommodationreviewservice.adapters.persistance.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OutgoingReviewDto {

  private String reviewId;
  private String accommodationId;
  private String comment;
  private Double rating;

  public static OutgoingReviewDto fromDomain(ReviewEntity reviewEntity) {
    return new OutgoingReviewDto(reviewEntity.getReviewId(), reviewEntity.getAccommodationId(),
        reviewEntity.getComment(), reviewEntity.getRating());
  }
}
