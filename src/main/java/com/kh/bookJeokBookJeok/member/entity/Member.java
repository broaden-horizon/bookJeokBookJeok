package com.kh.bookJeokBookJeok.member.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import lombok.*;


import javax.persistence.*;


@AllArgsConstructor
@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    @Id
    private Long memberId;
    @Column(nullable = false, updatable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String nickname;
    private int point = 0;
    @Column(name = "LEVELS")
    private int level = 1;
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;


    @Getter
    @AllArgsConstructor
    public enum MemberStatus {
        MEMBER_ACTIVE(1, "정상"),
        MEMBER_INACTIVE(2, "휴면"),
        MEMBER_WITHDRAW(3, "탈퇴");

        private int code;
        private String description;
    }
}
