package com.kh.bookJeokBookJeok.wish.controller;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.bookSearch.service.BookSearchService;
import com.kh.bookJeokBookJeok.dto.SingleResponseDto;
import com.kh.bookJeokBookJeok.review.mapper.ReviewMapper;
import com.kh.bookJeokBookJeok.wish.dto.WishDto;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import com.kh.bookJeokBookJeok.wish.mapper.WishlistMapper;
import com.kh.bookJeokBookJeok.wish.service.WishService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@AllArgsConstructor
@RequestMapping("/wishlist")
@Validated
public class WishController {
  private final WishService wishService;
  private final WishlistMapper wishlistMapper;
  private final BookSearchService bookSearchService;
  private final ReviewMapper reviewMapper;

    /**
     * 위시리스트 저장기능
     *
     * 위시리스트를 저장하면서, 책 정보를 함께 저장합니다.
     *
     * @param post
     * @return ResponseEntity
     */
  @PostMapping
  public ResponseEntity postWish(@Valid @RequestBody WishDto.Post post) {
    Wish wish = wishlistMapper.wishPostToWish(post);
    Wish wishCreated = wishService.create(wish);

    return new ResponseEntity(wishlistMapper.wishToSimpleResponse(wishCreated),
        HttpStatus.CREATED);
  }

  //위시 수정
  @PatchMapping("/{wishlist-id}")
  public ResponseEntity patchWish(@PathVariable("wish-id") @Positive Long wishId,
                                  @Valid @RequestBody WishDto.Patch patch) {
    Wish wish = wishlistMapper.wishPatchToWish(patch);
    Wish response = wishService.update(wish, wishId);
    return new ResponseEntity(wishlistMapper.wishToSimpleResponse(response), HttpStatus.OK);
  }
//
//  //위시 조회
//  @GetMapping("/{wishlist-id}")
//  public ResponseEntity getWish(@PathVariable("wish-id") @Positive Long wishId) {
//    Wish wish = wishService.getWish(wishId);
//    BookSearchResponseDto.Item bookResponse = bookSearchService.searchWithIsbn(wish.getIsbn());
//    WishDto.ResponseWithBook response = wishlistMapper.wishToResponse(wish, bookResponse);
//
//    return new ResponseEntity(new SingleResponseDto(response), HttpStatus.OK);
//  }

  // todo
  @GetMapping
  public void getWishlists(@Positive long memberId) {

  }

  //위시 삭제
  @DeleteMapping("/{wishlistId}")
  public void wishlistDelete(@PathVariable long wishId) {

  }


}
