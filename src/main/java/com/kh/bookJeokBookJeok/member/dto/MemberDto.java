package com.kh.bookJeokBookJeok.member.dto;

import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.util.validator.NotBlankIfNoText;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MemberDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        @NotBlankIfNoText
        private String password;
        @NotBlankIfNoText
        @Size(max = 20, message = "10글자 이하여야합니다.")
        private String nickname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        @NotBlank(message = "공백이 아니어야합니다.")
        @Email
        private String email;
        @NotBlank(message = "공백이 아니어야합니다.")
        private String password;
        @NotBlank(message = "공백이 아니어야합니다.")
        @Size(max = 20, message = "10글자 이하여야합니다.")
        private String nickname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long memberId;
        private String email;
        private String nickname;
        private int point;
        private int level;
        private Member.MemberStatus memberStatus;
    }

}
