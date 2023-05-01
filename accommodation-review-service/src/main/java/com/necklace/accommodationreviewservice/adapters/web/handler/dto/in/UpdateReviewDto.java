package com.necklace.accommodationreviewservice.adapters.web.handler.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateReviewDto {

  private String comment;
  private Double rating;

//  public Review toDomain() {
//    return new Review( comment, rating);
//  }
}
