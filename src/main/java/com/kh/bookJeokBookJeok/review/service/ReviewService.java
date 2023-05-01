package com.kh.bookJeokBookJeok.review.service;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.service.BookService;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.service.MemberService;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.review.repository.ReviewRepository;
import com.kh.bookJeokBookJeok.wish.service.WishService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
  public Review create(Review review, Long memberId) {
    Member member = memberService.findVerifiedMember(memberId);
    Book book = bookService.createIfAbsent(review.getBook());
    wishService.changeToReviewed(member, book);
    review.setMember(member);
    review.setBook(book);
    return reviewRepository.save(review);
  }
//
//    public Review read(long wishlistId) {
//        Wish wish = new Wish();
//        wish.setWishlistId(wishlistId);
//        Optional<Review> optionalReview = reviewRepository.findByWish(wish);
//        return optionalReview.orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));
//    }
}
