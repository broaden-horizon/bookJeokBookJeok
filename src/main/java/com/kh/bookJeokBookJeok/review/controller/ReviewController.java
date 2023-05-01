package com.kh.bookJeokBookJeok.review.controller;


import com.kh.bookJeokBookJeok.authentication.MemberDetailsService;
import com.kh.bookJeokBookJeok.dto.SingleResponseDto;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.review.mapper.ReviewMapper;
import com.kh.bookJeokBookJeok.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {
    private ReviewMapper reviewMapper;
    private ReviewService reviewService;

    /**
     * 리뷰를 저장합니다.
     *
     * 리뷰한 책 정보가 db에 없다면 생성합니다.
     * @param post
     * @param principal
     * @return
     */
    @PostMapping
    public ResponseEntity postReview(@RequestBody ReviewDto.Post post,
                                     @AuthenticationPrincipal MemberDetailsService.MemberDetails principal) {
        Review review = reviewMapper.postToReview(post);
        review = reviewService.create(review, principal.getMemberId());
        ReviewDto.Response response = reviewMapper.reviewToResponse(review);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }
}
