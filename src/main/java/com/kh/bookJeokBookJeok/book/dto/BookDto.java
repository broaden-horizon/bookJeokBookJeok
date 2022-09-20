package com.kh.bookJeokBookJeok.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class BookDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        @NotBlank
        private String title;
        private String author;
        private String publisher;
        @NotBlank
        private String isbn;
        private String description;
        private String image;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookResponseDto {
        private Long bookId;
        private int numberOfWishes;
        private String title;
        private String author;
        private String publisher;
        private String isbn;
        private String description;
        private String image;
    }
}
