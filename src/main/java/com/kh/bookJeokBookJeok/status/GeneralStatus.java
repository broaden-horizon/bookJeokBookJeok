package com.kh.bookJeokBookJeok.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeneralStatus {
    ACTIVE(1, "정상"),
    DELETED(2, "삭제");

    private int statusCode;
    private String description;

}
