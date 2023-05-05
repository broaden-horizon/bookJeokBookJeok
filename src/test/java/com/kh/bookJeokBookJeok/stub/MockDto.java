package com.kh.bookJeokBookJeok.stub;

import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.wish.dto.WishDto;

import java.time.LocalDate;

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
  public static class Wish {
    public static WishDto.Post getPost(String isbn) {
      return WishDto.Post.builder()
          .dueDate(LocalDate.now())
          .isbn(isbn)
          .title("Harry Poter")
          .author("jkRolling")
          .build();
    }

  }
}
