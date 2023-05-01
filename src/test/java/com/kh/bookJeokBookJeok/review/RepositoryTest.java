package com.kh.bookJeokBookJeok.review;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.repository.BookRepository;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.review.repository.ReviewRepository;
import com.kh.bookJeokBookJeok.stub.MockEntity;
import com.kh.bookJeokBookJeok.wish.repository.WishRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {
  @Autowired
  private ReviewRepository reviewRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private WishRepository wishRepository;
  @Autowired
  private BookRepository bookRepository;
  private Member member;

  @BeforeAll
  void init() {
    Member member = MockEntity.getMember();
    member = memberRepository.save(member);
  }

  @Test
  @DisplayName("기본 저장 테스트")
  void saveTest() {
    String isbn = "1234";
    Book book = MockEntity.getBook(isbn);
    book = bookRepository.save(book);

    Review review = MockEntity.getReview(member, book);
    reviewRepository.save(review);

    Review savedReview = reviewRepository.findAll().get(0);
    assertThat(savedReview.getBook().getIsbn())
        .isEqualTo(isbn);
  }
}
