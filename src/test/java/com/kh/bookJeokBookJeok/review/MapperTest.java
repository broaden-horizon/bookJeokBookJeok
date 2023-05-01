package com.kh.bookJeokBookJeok.review;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.review.mapper.ReviewMapper;
import com.kh.bookJeokBookJeok.stub.MockDto;
import com.kh.bookJeokBookJeok.stub.MockEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MapperTest {
  @Autowired
  private ReviewMapper reviewMapper;

  @Test
  void postToReviewTest() {
    // given
    String isbn = "1234";
    ReviewDto.Post post = MockDto.Review.getPost(isbn);

    // when
    Review review = reviewMapper.postToReview(post);

    // then
    assertThat(review.getTitle())
        .isEqualTo(post.getTitle());
    assertThat(review.getBook().getIsbn())
        .isEqualTo(isbn);
  }

  @Test
  void reviewToResponseTest() {
    // given
    String isbn = "1234b";
    Book book = MockEntity.getBook(isbn);
    Member member = MockEntity.getMember();
    Review review = MockEntity.getReview(member, book);

    // when
    ReviewDto.Response response = reviewMapper.reviewToResponse(review);

    // then
    assertThat(response.getBook().getIsbn())
        .isEqualTo(isbn);
    assertThat(response.getTitle())
        .isEqualTo(review.getTitle());
  }
}
