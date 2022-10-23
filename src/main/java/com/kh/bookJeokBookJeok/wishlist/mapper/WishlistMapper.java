package com.kh.bookJeokBookJeok.wishlist.mapper;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.wishlist.dto.WishlistDto;
import com.kh.bookJeokBookJeok.wishlist.entity.Wishlist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishlistMapper {
    Wishlist wishlistPostToWishlist(WishlistDto.Post post);
    Wishlist wishlistPatchToWishlist(WishlistDto.Patch patch);

    default WishlistDto.ResponseWithBookAndReview generateComplexResponse(Wishlist wishlist, BookSearchResponseDto.Item book, ReviewDto.Response review) {
        WishlistDto.ResponseWithBookAndReview response = new WishlistDto.ResponseWithBookAndReview();

        response.setWishlistId(wishlist.getWishlistId());
        response.setMemberId(wishlist.getMember().getMemberId());
        response.setMemberEmail(wishlist.getMember().getEmail());
        response.setDueDate(wishlist.getDueDate());
        response.setIsNotice(wishlist.isNotice() ? "Yes" : "No");
        response.setStatus(wishlist.getStatus());

        response.setBook(book);
        response.setReview(review);

        return response;
    }


    default WishlistDto.SimpleResponse wishListToSimpleResponse(Wishlist wishlist) {
        WishlistDto.SimpleResponse simpleResponse = new WishlistDto.SimpleResponse();
        simpleResponse.setWishlistId(wishlist.getWishlistId());
        simpleResponse.setMemberId(wishlist.getMember().getMemberId());
        simpleResponse.setMemberEmail(wishlist.getMember().getEmail());
        simpleResponse.setIsbn(wishlist.getIsbn());
        simpleResponse.setDueDate(wishlist.getDueDate());
        simpleResponse.setIsNotice(wishlist.isNotice() ? "Yes" : "No");
        simpleResponse.setStatus(wishlist.getStatus());
        return simpleResponse;
    }
}
