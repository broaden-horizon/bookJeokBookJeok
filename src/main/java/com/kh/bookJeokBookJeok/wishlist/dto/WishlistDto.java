package com.kh.bookJeokBookJeok.wishlist.dto;

import com.kh.bookJeokBookJeok.status.GeneralStatus;
import com.kh.bookJeokBookJeok.util.validator.NotBlankIfNoText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class WishlistDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class SimpleResponse {
        private Long wishlistId;
        private Long memberId;
        private String memberEmail;
        private String isbn;
        private LocalDate dueDate;
        private String isNotice;
        private GeneralStatus status;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Post {
        @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "[yyyy-mm-dd] format required")
        private String dueDate;
        @Pattern(regexp = "^[nNyY]$", message = "only y or Y or x or X")
        private String isNotice;
        @NotBlank(message = "isbn required")
        private String isbn;
    }

    static public class Option {
        @NotBlank
        private Long wishlistId;
        @NotBlankIfNoText
        private LocalDate dueDate;
        @NotBlankIfNoText
        private String isNotice;
    }
}
