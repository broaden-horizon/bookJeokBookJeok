package com.kh.bookJeokBookJeok.wish.dto;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.status.GeneralStatus;
import com.kh.bookJeokBookJeok.util.validator.NotBlankIfNoText;
import com.kh.bookJeokBookJeok.util.validator.NullOrPattern;
import lombok.*;


import javax.validation.constraints.NotBlank;

import java.time.LocalDate;

public class WishlistDto {
    @Getter
    static public class Patch {
        @NullOrPattern(pattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "[yyyy-mm-dd] format required")
        private String dueDate;
        @NullOrPattern(pattern = "^[nNyY]$", message = "only y or Y or x or X")
        private String isNotice;
    }

    @Getter
    @Setter
    static public class ResponseWithBookAndReview {
        private Long wishlistId;
        private Long memberId;
        private String memberEmail;
        private LocalDate dueDate;
        private String isNotice;
        private GeneralStatus status = GeneralStatus.ACTIVE;
        private BookSearchResponseDto.Item book;
        private ReviewDto.Response review;
    }

    @Getter
    @Setter
    static public class SimpleResponse {
        private Long wishlistId;
        private Long memberId;
        private String memberEmail;
        private String isbn;
        private LocalDate dueDate;
        private String isNotice;
        private GeneralStatus status = GeneralStatus.ACTIVE;
    }
    @Getter
    @AllArgsConstructor
    static public class Post {
        @NullOrPattern(pattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "[yyyy-mm-dd] format required")
        private String dueDate;
        @NullOrPattern(pattern = "^[nNyY]$", message = "only y or Y or x or X")
        private String isNotice;
        @NotBlank(message = "isbn required")
        private String isbn;
    }
    @Getter
    static public class Option {
        @NotBlank
        private Long wishlistId;
        @NotBlankIfNoText
        private LocalDate dueDate;
        @NotBlankIfNoText
        private String isNotice;
    }
}