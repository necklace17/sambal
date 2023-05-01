package com.necklace.accommodationreviewservice.adapters.persistance.entity;

import com.necklace.accommodationreviewservice.reviews.domain.Review;
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
  private String accommodationId;
  private String comment;
  private Double rating;

  public static ReviewEntity fromDomain(Review review) {
    return new ReviewEntity(null, review.getAccommodationId(), review.getComment(),
        review.getRating());
  }

}
