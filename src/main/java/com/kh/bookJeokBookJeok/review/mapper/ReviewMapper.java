package com.kh.bookJeokBookJeok.review.mapper;

import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    public ReviewDto.Response reviewToResponse(Review review);
    default public Review postToReview(ReviewDto.Post post) {
        Review review = new Review();
        review.setTitle(post.getTitle());
        review.setWriting(post.getWriting());
        Wish wish = new Wish();
        wish.setWishlistId(post.getWishlistId());
        review.setWish(wish);
        return review;
    }
}
