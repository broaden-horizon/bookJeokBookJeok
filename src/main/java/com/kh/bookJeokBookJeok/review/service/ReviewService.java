package com.kh.bookJeokBookJeok.review.service;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.service.BookService;
import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.service.MemberService;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.review.repository.ReviewRepository;
import com.kh.bookJeokBookJeok.wish.service.WishService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewService {
  private ReviewRepository reviewRepository;
  private MemberService memberService;
  private BookService bookService;
  private WishService wishService;

  /**
   * 리뷰를 저장합니다.
   * <p>
   * DB 책정보가 없다면 새로 생성한후 연관관계를 갖습니다.
   * 회원의 위시리스트에 있는 책이라면, isReview 멤버를 true로 변경합니다.
   *
   * @param review
   * @param memberId
   * @return Review - 저장된 엔티티를 리턴합니다.
   */
  @Transactional
  public Review create(Review review, Long memberId) {
    Member member = memberService.findVerifiedMember(memberId);
    Book book = bookService.createIfAbsent(review.getBook());
    wishService.changeToReviewed(member, book);
    review.setMember(member);
    review.setBook(book);
    return reviewRepository.save(review);
  }

  /**
   * 리뷰 아이디에 맞는 리뷰를 리턴합니다.
   *
   * @param reviewId
   * @param memberId
   * @throws BusinessLogicException - 리뷰 아이디와 유저 아이디에 해당하는 리뷰가 없을 때
   * @return Review
   */
  @Transactional(readOnly = true)
  public Review getReview(Long reviewId, Long memberId) {
    Review review = findVerifiedReview(reviewId, memberId);
    return review;
  }

  /**
   * 자신이 작성한 모든 리뷰를 리턴합니다.
   *
   * @param memberId 회원 아이디
   * @return 자신이 작성한 리뷰 리스트
   */
  @Transactional(readOnly = true)
  public Page<Review> getReviews(Long memberId, Pageable pageable) {
    Member member = memberService.findVerifiedMember(memberId);
    Page<Review> reviews = reviewRepository.findByMember(member, pageable);
    return reviews;
  }

  /**
   * 리뷰를 수정한다.
   *
   * @param reviewId
   * @param memberId
   * @param patch
   * @throws BusinessLogicException - 리뷰 아이디와 유저 아이디에 해당하는 리뷰가 없을 때
   * @return 수정된 리뷰 엔티티
   */
  @Transactional
  public Review updateReview(Long reviewId, Long memberId, ReviewDto.Patch patch) {
    Review review = findVerifiedReview(reviewId, memberId);
    Optional.ofNullable(patch.getTitle()).ifPresent((title) -> review.setTitle(title));
    Optional.ofNullable(patch.getWriting()).ifPresent((writing) -> review.setWriting(writing));
    return review;
  }

  /**
   * 리뷰의 상태를 deleted로 바꿉니다.
   *
   * @param reviewId
   * @param memberId
   * @throws BusinessLogicException - 리뷰 아이디와 유저 아이디에 해당하는 리뷰가 없을 때
   */
  @Transactional
  public void deleteReview(Long reviewId, Long memberId) {
    Review review = findVerifiedReview(reviewId, memberId);
    review.toDeleted();
  }

  private Review findVerifiedReview(Long reviewId, Long memberId) {
    Optional<Review> review = reviewRepository.findByReviewIdAndMember(reviewId, memberId);
    return review.orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));
  }
}
