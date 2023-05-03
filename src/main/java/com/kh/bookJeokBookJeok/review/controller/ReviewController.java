package com.kh.bookJeokBookJeok.review.controller;


import com.kh.bookJeokBookJeok.authentication.MemberDetailsService;
import com.kh.bookJeokBookJeok.dto.MultiResponseDto;
import com.kh.bookJeokBookJeok.dto.SingleResponseDto;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.review.mapper.ReviewMapper;
import com.kh.bookJeokBookJeok.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/reviews")
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
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity postReview(@RequestBody ReviewDto.Post post,
                                     @AuthenticationPrincipal MemberDetailsService.MemberDetails principal) {
        Review review = reviewMapper.postToReview(post);
        review = reviewService.create(review, principal.getMemberId());
        ReviewDto.Response response = reviewMapper.reviewToResponse(review);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    /**
     * 특정 리뷰를 조회합니다.
     * 본인이 작성한 리뷰만 조회할 수 있습니다.
     * @param reviewId
     * @param principal
     * @return ResponseEntity
     */
    @GetMapping("/{review-id}")
    public ResponseEntity getReview(@PathVariable("review-id") Long reviewId,
                                    @AuthenticationPrincipal MemberDetailsService.MemberDetails principal) {
        Review review = reviewService.getReview(reviewId, principal.getMemberId());
        ReviewDto.Response response = reviewMapper.reviewToResponse(review);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    /**
     * 본인이 작성한 모든 리뷰를 페이지 단위로 조회합니다.
     *
     * @param page
     * @param size
     * @param principal
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity getReviews(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size,
                                     @AuthenticationPrincipal MemberDetailsService.MemberDetails principal) {
        Page<Review> reviews = reviewService.getReviews(principal.getMemberId(), PageRequest.of(page - 1, size));
        List<ReviewDto.Response> responses = reviewMapper.reviewsToResponses(reviews.getContent());
        return new ResponseEntity(new MultiResponseDto<>(responses, reviews), HttpStatus.OK);
    }
}
