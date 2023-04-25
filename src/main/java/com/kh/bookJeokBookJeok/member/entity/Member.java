package com.kh.bookJeokBookJeok.member.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
  @OneToMany(mappedBy = "member")
  private List<Wish> wishes = new ArrayList<>();
  @OneToMany(mappedBy = "member")
  private List<Review> review = new ArrayList<>();

  @Builder
  public Member(String email, String password, String nickname, List<String> roles) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.roles = roles;
  }

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
