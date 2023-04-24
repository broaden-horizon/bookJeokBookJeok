package com.kh.bookJeokBookJeok.wish.controller;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.bookSearch.service.BookSearchService;
import com.kh.bookJeokBookJeok.dto.SingleResponseDto;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.review.mapper.ReviewMapper;
import com.kh.bookJeokBookJeok.wish.dto.WishlistDto;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import com.kh.bookJeokBookJeok.wish.mapper.WishlistMapper;
import com.kh.bookJeokBookJeok.wish.service.WishlistService;
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
        Wish wish = wishlistMapper.wishlistPostToWishlist(post);
        Wish wishCreated = wishlistService.create(wish);

        return new ResponseEntity(wishlistMapper.wishListToSimpleResponse(wishCreated),
                HttpStatus.CREATED);
    }
    //위시리스트 수정
    @PatchMapping("/{wishlist-id}")
    public ResponseEntity patchWishlist(@PathVariable("wishlist-id") @Positive Long wishlistId,
                                        @Valid @RequestBody WishlistDto.Patch patch) {
        Wish wish = wishlistMapper.wishlistPatchToWishlist(patch);
        wish.setWishlistId(wishlistId);
        Wish response = wishlistService.update(wish);
        return new ResponseEntity(wishlistMapper.wishListToSimpleResponse(response), HttpStatus.OK);
    }
    //위시리스트 조회
    @GetMapping("/{wishlist-id}")
    public ResponseEntity getWishlist(@PathVariable("wishlist-id") @Positive Long wishlistId) {
        Wish wish = wishlistService.getWishlist(wishlistId);

        BookSearchResponseDto.Item bookResponse = bookSearchService.searchWithIsbn(wish.getIsbn());
        ReviewDto.Response reviewResponse = reviewMapper.reviewToResponse(wish.getReview());

        WishlistDto.ResponseWithBookAndReview response = wishlistMapper.generateComplexResponse(wish, bookResponse, reviewResponse);

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
