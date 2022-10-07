package com.kh.bookJeokBookJeok.wishlist.dto;

import com.kh.bookJeokBookJeok.util.validator.NotBlankIfNoText;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class WishlistDto {
    static public class Option {
        @NotBlank
        private Long wishlistId;
        @NotBlankIfNoText
        private LocalDateTime dueDate;
        @NotBlankIfNoText
        private String isNotice;
    }
}
