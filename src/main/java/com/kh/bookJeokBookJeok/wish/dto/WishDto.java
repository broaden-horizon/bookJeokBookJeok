package com.kh.bookJeokBookJeok.wish.dto;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.status.GeneralStatus;
import com.kh.bookJeokBookJeok.util.validator.NoOnlyBlank;
import com.kh.bookJeokBookJeok.util.validator.NullOrPattern;
import lombok.*;


import javax.validation.constraints.NotBlank;

import java.time.LocalDate;

public class WishDto {
    @Getter
    static public class Patch {
        @NullOrPattern(pattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "[yyyy-mm-dd] format required")
        private String dueDate;
        @NullOrPattern(pattern = "^[nNyY]$", message = "only y or Y or x or X")
        private String isNotice;
    }

    @Getter
    @Setter
    static public class ResponseWithBook {
        private Long wishlistId;
        private Long memberId;
        private String memberEmail;
        private LocalDate dueDate;
        private String isNotice;
        private String isReviewed;
        private BookSearchResponseDto.Item book;
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
        // dueDate 2주전 / 1주전 / 3일전 / 하루전에 독서 알림 기능 작동 여부
        @NullOrPattern(pattern = "^[nNyY]$", message = "only y or Y or x or X")
        private String isNotice;
        @NotBlank(message = "isbn required")
        private String isbn;
        @NotBlank(message = "title required")
        private String title;
        @NotBlank(message = "title required")
        private String author;
    }
    @Getter
    static public class Option {
        @NotBlank
        private Long wishlistId;
        @NoOnlyBlank
        private LocalDate dueDate;
        @NoOnlyBlank
        private String isNotice;
    }
}