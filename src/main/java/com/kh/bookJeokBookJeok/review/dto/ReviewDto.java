package com.kh.bookJeokBookJeok.review.dto;

import com.kh.bookJeokBookJeok.book.dto.BookDto;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class ReviewDto {
    @Getter
    @Builder
    static public class Post {
        @NotBlank
        private String title;
        @NotBlank
        private String writing;
        @NotBlank
        private String bookTitle;
        @NotBlank
        private String isbn;
        @NotBlank
        private String author;
    }
    @Getter
    @Builder
    static public class Response {
        private Long reviewId;
        private String title;
        private String writing;
        private BookDto book;
    }
}
