package com.kh.bookJeokBookJeok.review.mapper;

import com.kh.bookJeokBookJeok.book.dto.BookDto;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.review.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
  ReviewDto.Response reviewToResponse(Review review);
  List<ReviewDto.Response> reviewsToResponses(List<Review> reviews);

  BookDto bookToBookDto(ReviewDto.Post post);

  default Review postToReview(ReviewDto.Post post) {
    return Review.builder()
        .book(postToBook(post))
        .title(post.getTitle())
        .writing(post.getWriting())
        .build();
  }

  Book postToBook(ReviewDto.Post post);
}
