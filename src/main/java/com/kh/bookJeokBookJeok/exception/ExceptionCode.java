package com.kh.bookJeokBookJeok.exception;

import com.kh.bookJeokBookJeok.wishlist.entity.Wishlist;
import lombok.Getter;

@Getter
public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member already exists"),
    MEMBER_WITH_EMAIL_EXISTS(409, "Member with the email already exists"),
    MEMBER_WITH_NICKNAME_EXISTS(409, "Member with the nickname already exists"),
    BOOK_WITH_ISBN_EXISTS(409, "Book with the ISBN already exists"),
    BOOK_NOT_FOUND(409, "Book not found"),
    AUTHENTICATION_NOT_FOUND(403, "Authentication not Found"),
    WISHLIST_EXISTS(409, "Wishlist already exists");

    private int code;
    private String description;

    ExceptionCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
