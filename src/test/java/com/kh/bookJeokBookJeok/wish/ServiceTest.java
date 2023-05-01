package com.kh.bookJeokBookJeok.wish;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.repository.BookRepository;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import com.kh.bookJeokBookJeok.stub.MockEntity;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import com.kh.bookJeokBookJeok.wish.repository.WishRepository;
import com.kh.bookJeokBookJeok.wish.service.WishService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceTest {
  @Autowired
  private WishService wishService;
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private WishRepository wishRepository;

  private Member member;
  private Book book;
  private Wish wish;
  private String isbn;

  @BeforeAll
  void init() {
    isbn = "1234";
    book = bookRepository.save(MockEntity.getBook(isbn));
    member = memberRepository.save(MockEntity.getMember());
    wish = wishRepository.save(MockEntity.getWish(member, book));
  }
  @Test
  void changeToReviewedTest() {
    //when
    wishService.changeToReviewed(member, book);

    //then
    Wish wishFound = wishRepository.findById(wish.getWishlistId()).get();
    assertThat(wishFound.isReviewed())
        .isTrue();
  }
}
