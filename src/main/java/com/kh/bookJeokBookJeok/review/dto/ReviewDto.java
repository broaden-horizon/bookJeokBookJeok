package com.kh.bookJeokBookJeok.review.dto;

import com.kh.bookJeokBookJeok.util.validator.NullOrPattern;
import com.kh.bookJeokBookJeok.wishlist.entity.Wishlist;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

public class ReviewDto {
    @Getter
    static public class Post {
        @NotBlank
        private Long wishlistId;
        @NotBlank
        private String title;
        @NotBlank
        private String writing;
    }
    @Getter
    @Setter
    static public class Response {
        private Long reviewId;
        private String title;
        private String writing;
    }
}
