package com.kh.bookJeokBookJeok.wish.controller;

import com.kh.bookJeokBookJeok.authentication.MemberDetailsService;
import com.kh.bookJeokBookJeok.bookSearch.service.BookSearchService;
import com.kh.bookJeokBookJeok.dto.SingleResponseDto;
import com.kh.bookJeokBookJeok.review.mapper.ReviewMapper;
import com.kh.bookJeokBookJeok.wish.dto.WishDto;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import com.kh.bookJeokBookJeok.wish.mapper.WishlistMapper;
import com.kh.bookJeokBookJeok.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequiredArgsConstructor
@RequestMapping("/wishes")
@Validated
public class WishController {
  private final WishService wishService;
  private final WishlistMapper wishlistMapper;
  private final BookSearchService bookSearchService;
  private final ReviewMapper reviewMapper;

    /**
     * 위시 저장기능
     *
     * 위시리스트를 저장하면서, 책 정보를 함께 저장합니다.
     *
     * @param post
     * @return ResponseEntity
     */
  @PostMapping
  public ResponseEntity postWish(@Valid @RequestBody WishDto.Post post,
                                 @AuthenticationPrincipal MemberDetailsService.MemberDetails principal) {
    Wish wish = wishlistMapper.postToWish(post);
    Wish response = wishService.create(wish, principal.getMemberId());

    return new ResponseEntity(new SingleResponseDto(wishlistMapper.wishToResponse(response)),
        HttpStatus.CREATED);
  }

  /**
   * 위시 수정
   *
   * dueDate(특정 날짜까지 읽기로 등록한 날짜)를 수정합니다.
   *
   * @param wishId
   * @param patch
   * @param principal
   * @return 해당 위시
   */
  @PatchMapping("/{wish-id}")
  public ResponseEntity patchWish(@PathVariable("wish-id") @Positive Long wishId,
                                  @Valid @RequestBody WishDto.Patch patch,
                                  @AuthenticationPrincipal MemberDetailsService.MemberDetails principal) {
    Wish wish = wishlistMapper.wishPatchToWish(patch);
    wish = wishService.update(wish, wishId, principal.getMemberId());
    WishDto.Response response = wishlistMapper.wishToResponse(wish);
    return new ResponseEntity(new SingleResponseDto(response), HttpStatus.OK);
  }


  /**
   * 본인이 작성한 특정 위시를 조회합니다.
   *
   * @param wishId
   * @param principal
   * @return 해당 위시
   */
  @GetMapping("/{wish-id}")
  public ResponseEntity getWish(@PathVariable("wish-id") @Positive Long wishId,
                                @AuthenticationPrincipal MemberDetailsService.MemberDetails principal) {
    Wish wish = wishService.retrieve(wishId, principal.getMemberId());
    WishDto.Response response = wishlistMapper.wishToResponse(wish);
    return new ResponseEntity(new SingleResponseDto(response), HttpStatus.OK);
  }

  // todo
  @GetMapping
  public void getWishlists(@Positive long memberId) {

  }

  //위시 삭제
  @DeleteMapping("/{wishlistId}")
  public void wishlistDelete(@PathVariable long wishId) {

  }


}
