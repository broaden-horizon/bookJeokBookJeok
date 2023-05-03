package com.kh.bookJeokBookJeok.stub;

import com.kh.bookJeokBookJeok.review.dto.ReviewDto;

public class MockDto {
  public static class Review {
    public static ReviewDto.Post getPost(String isbn) {
      return ReviewDto.Post.builder()
          .author("j.k.rolling")
          .bookTitle("harryPotter")
          .isbn(isbn)
          .title("Happy Harry")
          .writing("is great!")
          .build();
    }
  }

  public static ReviewDto.Patch getPatch() {
    return ReviewDto.Patch.builder()
        .title("new Title")
        .writing("new Writing")
        .build();
  }
}
