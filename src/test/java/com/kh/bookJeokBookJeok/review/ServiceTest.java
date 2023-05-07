package com.kh.bookJeokBookJeok.review;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.service.BookService;
import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.service.MemberService;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.review.repository.ReviewRepository;
import com.kh.bookJeokBookJeok.review.service.ReviewService;
import com.kh.bookJeokBookJeok.stub.MockEntity;
import com.kh.bookJeokBookJeok.wish.service.WishService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
  @InjectMocks
  private ReviewService reviewService;
  @Mock
  private ReviewRepository reviewRepository;
  @Mock
  private MemberService memberService;
  @Mock
  private BookService bookService;
  @Mock
  private WishService wishService;

  @Test
  void createTest() {
    // given
    Long memberId = 1L;
    String isbn = "1234";
    Book book = MockEntity.getBook(isbn);
    Member member = MockEntity.getMember();
    Review review = MockEntity.getReview(member, book);
    when(memberService.findVerifiedMember(memberId)).thenReturn(member);
    when(bookService.createIfAbsent(any())).thenReturn(book);

    // when & then
    assertThatNoException().isThrownBy(() -> reviewService.create(review, memberId));
  }

  @Test
  void getReviewTest() {
    // given
    Long memberId = 1L;
    Long reviewId = 2L;
    Member member = MockEntity.getMember(memberId);
    Review review = MockEntity.getReview(member, reviewId);

    when(memberService.findVerifiedMember(memberId)).thenReturn(member);
    when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

    // when
    Review reviewGotten = reviewService.getReview(reviewId, memberId);

    // then
    assertThat(reviewGotten.getReviewId()).isEqualTo(reviewId);
  }

  @Test
  @DisplayName("getReview 예외 테스트 - 본인이 리뷰의 작성자가 아닌 경우")
  void getReviewTest_exception1() {
    // given
    Long memberId = 1L;
    Long reviewId = 2L;
    Member member = MockEntity.getMember(memberId);
    Review review = MockEntity.getReview(member, reviewId);

    Long wrongMemberId = 333L;
    Member wrongMember = MockEntity.getMember(wrongMemberId);

    when(memberService.findVerifiedMember(wrongMemberId)).thenReturn(wrongMember);
    when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

    // when & then
    assertThatThrownBy(() -> {
      Review reviewGotten = reviewService.getReview(reviewId, wrongMemberId);
    }).isInstanceOf(BusinessLogicException.class);
  }
}
