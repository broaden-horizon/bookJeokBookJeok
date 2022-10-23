package com.kh.bookJeokBookJeok.review.controller;


import com.kh.bookJeokBookJeok.dto.SingleResponseDto;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.review.mapper.ReviewMapper;
import com.kh.bookJeokBookJeok.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping
    public ResponseEntity postReview(@RequestBody ReviewDto.Post post) {
        Review review = reviewMapper.postToReview(post);
        Review response = reviewService.create(review);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }
}
