package com.kh.bookJeokBookJeok.wish.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.util.validator.NoOnlyBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class WishDto {
  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static public class Patch {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate dueDate;
  }


  @Getter
  @Builder
  static public class Post {
    //        @NullOrPattern(pattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "[yyyy-mm-dd] format required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate dueDate;
    @NotBlank(message = "isbn required")
    private String isbn;
    @NotBlank(message = "title required")
    private String title;
    @NotBlank(message = "title required")
    private String author;
  }

  @Getter
  static public class Option {
    @NotBlank
    private Long wishlistId;
    @NoOnlyBlank
    private LocalDate dueDate;
    @NoOnlyBlank
    private String isNotice;
  }

  @Getter
  @Setter
  static public class Response {
    private Long wishlistId;
    private LocalDate dueDate;
    private boolean isNotice;
    private String isReviewed;
    private Book book;
  }
}
