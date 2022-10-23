package com.kh.bookJeokBookJeok.wishlist.controller;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.bookSearch.service.BookSearchService;
import com.kh.bookJeokBookJeok.dto.SingleResponseDto;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.review.mapper.ReviewMapper;
import com.kh.bookJeokBookJeok.wishlist.dto.WishlistDto;
import com.kh.bookJeokBookJeok.wishlist.entity.Wishlist;
import com.kh.bookJeokBookJeok.wishlist.mapper.WishlistMapper;
import com.kh.bookJeokBookJeok.wishlist.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@AllArgsConstructor
@RequestMapping("/wishlist")
@Validated
public class WishlistController {
    private final WishlistService wishlistService;
    private final WishlistMapper wishlistMapper;
    private final BookSearchService bookSearchService;
    private final ReviewMapper reviewMapper;
    //위시리스트 저장
    @PostMapping
    public ResponseEntity postWishlist(@Valid @RequestBody WishlistDto.Post post) {
        Wishlist wishlist = wishlistMapper.wishlistPostToWishlist(post);
        Wishlist wishlistCreated = wishlistService.create(wishlist);

        return new ResponseEntity(wishlistMapper.wishListToSimpleResponse(wishlistCreated),
                HttpStatus.CREATED);
    }
    //위시리스트 수정
    @PatchMapping("/{wishlist-id}")
    public ResponseEntity patchWishlist(@PathVariable("wishlist-id") @Positive Long wishlistId,
                                        @Valid @RequestBody WishlistDto.Patch patch) {
        Wishlist wishlist = wishlistMapper.wishlistPatchToWishlist(patch);
        wishlist.setWishlistId(wishlistId);
        Wishlist response = wishlistService.update(wishlist);
        return new ResponseEntity(wishlistMapper.wishListToSimpleResponse(response), HttpStatus.OK);
    }
    //위시리스트 조회
    @GetMapping("/{wishlist-id}")
    public ResponseEntity getWishlist(@PathVariable("wishlist-id") @Positive Long wishlistId) {
        Wishlist wishlist = wishlistService.getWishlist(wishlistId);

        BookSearchResponseDto.Item bookResponse = bookSearchService.searchWithIsbn(wishlist.getIsbn());
        ReviewDto.Response reviewResponse = reviewMapper.reviewToResponse(wishlist.getReview());

        WishlistDto.ResponseWithBookAndReview response = wishlistMapper.generateComplexResponse(wishlist, bookResponse, reviewResponse);

        return new ResponseEntity(new SingleResponseDto(response), HttpStatus.OK);
    }
    @GetMapping
    public void getWishlists(@Positive long memberId) {

    }

    //위시리스트 삭제할 때
    @DeleteMapping("/{wishlistId}")
    public void wishlistDelete(@PathVariable long wishlistId) {

    }


}
