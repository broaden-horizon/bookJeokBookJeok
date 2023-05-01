package com.kh.bookJeokBookJeok.book.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookId;
  private String title;
  private String isbn;
  private String author;

  @Builder
  public Book(String title, String isbn, String author) {
    this.title = title;
    this.isbn = isbn;
    this.author = author;
  }
}
