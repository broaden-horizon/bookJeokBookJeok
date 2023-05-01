package com.kh.bookJeokBookJeok.stub;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.wish.entity.Wish;

import java.time.LocalDate;
import java.util.List;

public class MockEntity {
  public static Member getMember() {
    return Member.builder()
        .email("abcd@naver.com")
        .password("1234")
        .nickname("paul")
        .roles(List.of("ROLE_USER"))
        .build();
  }

  public static Member getMember(String email) {
    return Member.builder()
        .email(email)
        .password("1234")
        .nickname("paul")
        .roles(List.of("ROLE_USER"))
        .build();
  }

  public static Review getReview(Member member, Book book) {
    return Review.builder()
        .writing("하하")
        .title("하하")
        .book(book)
        .member(member)
        .build();
  }

  public static Wish getWish(Member member, Book book) {
    return Wish.builder()
        .dueDate(LocalDate.now().plusDays(3))
        .book(book)
        .isNotice(true)
        .member(member)
        .build();
  }

  public static Book getBook(String isbn) {
    return Book.builder()
        .author("J. K. Rolling")
        .isbn(isbn)
        .title("Harry Potter")
        .build();
  }
}
