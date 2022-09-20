package com.kh.bookJeokBookJeok.exception;

import lombok.Getter;
@Getter
public class BusinessLogicException extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;
    private Object rejectedValue;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public BusinessLogicException(ExceptionCode exceptionCode, Object rejectedValue) {
        this.exceptionCode = exceptionCode;
        this.rejectedValue = rejectedValue;
    }



}
