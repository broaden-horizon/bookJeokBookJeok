package com.kh.bookJeokBookJeok.review.dto;

import com.kh.bookJeokBookJeok.book.dto.BookDto;
import com.kh.bookJeokBookJeok.util.validator.NoOnlyBlank;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class ReviewDto {
    @Getter
    @Builder
    public static class Post {
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
    public static class Patch {
        @NoOnlyBlank
        private String title;
        @NoOnlyBlank
        private String writing;
    }

    @Getter
    @Builder
    public static class Response {
        private Long reviewId;
        private String title;
        private String writing;
        private BookDto book;
    }
}
