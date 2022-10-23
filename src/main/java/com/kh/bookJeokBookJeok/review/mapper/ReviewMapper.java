package com.kh.bookJeokBookJeok.review.mapper;

import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.wishlist.entity.Wishlist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    public ReviewDto.Response reviewToResponse(Review review);
    default public Review postToReview(ReviewDto.Post post) {
        Review review = new Review();
        review.setTitle(post.getTitle());
        review.setWriting(post.getWriting());
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(post.getWishlistId());
        review.setWishlist(wishlist);
        return review;
    }
}
