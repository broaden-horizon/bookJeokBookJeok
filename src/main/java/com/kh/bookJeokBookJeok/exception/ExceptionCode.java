package com.kh.bookJeokBookJeok.exception;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member already exists"),
    MEMBER_WITH_EMAIL_EXISTS(409, "Member with the email already exists"),
    MEMBER_WITH_NICKNAME_EXISTS(409, "Member with the nickname already exists"),
    BOOK_WITH_ISBN_EXISTS(409, "Book with the ISBN already exists"),
    BOOK_NOT_FOUND(409, "Book not found");

    private int code;
    private String description;

    ExceptionCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
