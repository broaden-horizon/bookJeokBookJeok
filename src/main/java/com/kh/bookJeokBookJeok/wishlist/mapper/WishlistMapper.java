package com.kh.bookJeokBookJeok.wishlist.mapper;

import com.kh.bookJeokBookJeok.wishlist.dto.WishlistDto;
import com.kh.bookJeokBookJeok.wishlist.entity.Wishlist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishlistMapper {
    Wishlist wishlistPostToWishlist(WishlistDto.Post post);
    Wishlist wishlistPatchToWishlist(WishlistDto.Patch patch);


    default WishlistDto.SimpleResponse wishListToSimpleResponse(Wishlist wishlist) {
        WishlistDto.SimpleResponse simpleResponse = new WishlistDto.SimpleResponse();
        simpleResponse.setWishlistId(wishlist.getWishlistId());
        simpleResponse.setMemberId(wishlist.getMember().getMemberId());
        simpleResponse.setMemberEmail(wishlist.getMember().getEmail());
        simpleResponse.setIsbn(wishlist.getIsbn());
        simpleResponse.setDueDate(wishlist.getDueDate());
        simpleResponse.setIsNotice(wishlist.isNotice() ? "yes" : "no");
        simpleResponse.setStatus(wishlist.getStatus());
        return simpleResponse;
    }
}
