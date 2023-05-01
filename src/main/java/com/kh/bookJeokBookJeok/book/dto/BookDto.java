package com.kh.bookJeokBookJeok.book.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDto {
  private String isbn;
  private String author;
  private String title;
}
