package com.kh.bookJeokBookJeok.member.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import lombok.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Wish> wishes = new ArrayList<>();
    public Member(Long memberId, String email, String password, List<String> roles) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

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
