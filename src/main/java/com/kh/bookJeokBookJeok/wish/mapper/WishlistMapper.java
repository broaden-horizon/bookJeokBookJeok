package com.kh.bookJeokBookJeok.wish.mapper;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.wish.dto.WishDto;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishlistMapper {
    Wish wishPostToWish(WishDto.Post post);
    Wish wishPatchToWish(WishDto.Patch patch);

    default WishDto.ResponseWithBook wishToResponse(Wish wish, BookSearchResponseDto.Item book) {
        WishDto.ResponseWithBook response = new WishDto.ResponseWithBook();

        response.setWishlistId(wish.getWishlistId());
        response.setMemberId(wish.getMember().getMemberId());
        response.setMemberEmail(wish.getMember().getEmail());
        response.setDueDate(wish.getDueDate());
        response.setIsNotice(wish.isNotice() ? "Yes" : "No");
        response.setIsReviewed(wish.isReviewed() ? "Yes" : "No");
        response.setBook(book);

        return response;
    }


    default WishDto.SimpleResponse wishToSimpleResponse(Wish wish) {
        WishDto.SimpleResponse simpleResponse = new WishDto.SimpleResponse();
        simpleResponse.setWishlistId(wish.getWishlistId());
        simpleResponse.setMemberId(wish.getMember().getMemberId());
        simpleResponse.setMemberEmail(wish.getMember().getEmail());

        simpleResponse.setDueDate(wish.getDueDate());
        simpleResponse.setIsNotice(wish.isNotice() ? "Yes" : "No");
        simpleResponse.setStatus(wish.getStatus());
        return simpleResponse;
    }
}
