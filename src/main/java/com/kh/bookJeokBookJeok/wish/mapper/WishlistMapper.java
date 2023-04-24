package com.kh.bookJeokBookJeok.wish.mapper;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.wish.dto.WishlistDto;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishlistMapper {
    Wish wishlistPostToWishlist(WishlistDto.Post post);
    Wish wishlistPatchToWishlist(WishlistDto.Patch patch);

    default WishlistDto.ResponseWithBookAndReview generateComplexResponse(Wish wish, BookSearchResponseDto.Item book, ReviewDto.Response review) {
        WishlistDto.ResponseWithBookAndReview response = new WishlistDto.ResponseWithBookAndReview();

        response.setWishlistId(wish.getWishlistId());
        response.setMemberId(wish.getMember().getMemberId());
        response.setMemberEmail(wish.getMember().getEmail());
        response.setDueDate(wish.getDueDate());
        response.setIsNotice(wish.isNotice() ? "Yes" : "No");
        response.setStatus(wish.getStatus());

        response.setBook(book);
        response.setReview(review);

        return response;
    }


    default WishlistDto.SimpleResponse wishListToSimpleResponse(Wish wish) {
        WishlistDto.SimpleResponse simpleResponse = new WishlistDto.SimpleResponse();
        simpleResponse.setWishlistId(wish.getWishlistId());
        simpleResponse.setMemberId(wish.getMember().getMemberId());
        simpleResponse.setMemberEmail(wish.getMember().getEmail());
        simpleResponse.setIsbn(wish.getIsbn());
        simpleResponse.setDueDate(wish.getDueDate());
        simpleResponse.setIsNotice(wish.isNotice() ? "Yes" : "No");
        simpleResponse.setStatus(wish.getStatus());
        return simpleResponse;
    }
}
