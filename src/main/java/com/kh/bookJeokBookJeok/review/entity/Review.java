package com.kh.bookJeokBookJeok.review.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewId;
  private String title;
  private String writing;
  private String isbn;
  private String bookTitle;
  private String author;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_ID")
  private Member member;
  @Setter
  @OneToOne(mappedBy = "review", cascade = CascadeType.PERSIST)
  private Wish wish;

  @Builder
  public Review(String title, String writing, String isbn, String bookTitle, String author, Member member) {
    this.title = title;
    this.writing = writing;
    this.isbn = isbn;
    this.bookTitle = bookTitle;
    this.author = author;
    this.member = member;
  }
}