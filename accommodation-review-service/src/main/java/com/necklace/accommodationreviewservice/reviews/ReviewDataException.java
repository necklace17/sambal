package com.necklace.accommodationreviewservice.reviews;

public class ReviewDataException extends RuntimeException {

  private String message;

  public ReviewDataException(String s) {
    super(s);
    this.message = s;
  }

}
