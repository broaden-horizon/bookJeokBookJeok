package com.kh.bookJeokBookJeok.review.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.status.GeneralStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewId;
  private GeneralStatus generalStatus = GeneralStatus.ACTIVE;
  @Setter
  private String title;
  @Setter
  private String writing;
  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOK_ID")
  private Book book;
  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @Builder
  public Review(String title, String writing, Member member, Book book) {
    this.title = title;
    this.writing = writing;
    this.member = member;
    this.book = book;
  }

  public void toActive() {
    this.generalStatus = GeneralStatus.ACTIVE;
  }

  public void toDeleted() {
    this.generalStatus = GeneralStatus.DELETED;
  }
}