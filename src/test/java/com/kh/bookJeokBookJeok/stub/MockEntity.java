package com.kh.bookJeokBookJeok.stub;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.helper.RandomGenerator;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

public class MockEntity {
  public static Member getMember(Long memberId) {
    Member member = getMember();
    ReflectionTestUtils.setField(member, "memberId", memberId);
    return member;
  }

  public static Member getMember() {
    return Member.builder()
        .email(RandomGenerator.randomStringGenerator() + "@gmail.com")
        .password("1234")
        .nickname(RandomGenerator.randomStringGenerator())
        .roles(List.of("ROLE_USER"))
        .build();
  }


  public static Member getMember(String email, String nickname) {
    return Member.builder()
        .email(email)
        .password("1234")
        .nickname(nickname)
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

  public static Review getReview(Member member, Long reviewId) {
    Book book = getBook("1234");
    Review review = getReview(member, book);
    ReflectionTestUtils.setField(review, "reviewId", reviewId);
    return review;
  }

  public static Wish getWish(Member member, Book book) {
    return Wish.builder()
        .dueDate(LocalDate.now().plusDays(3))
        .book(book)
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
