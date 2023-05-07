package com.kh.bookJeokBookJeok.wish.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.status.GeneralStatus;
import com.kh.bookJeokBookJeok.util.converter.BooleanConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Wish extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long wishId;
  @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_ID")
  private Member member;
  @Setter
  private LocalDate dueDate;
  @Setter
  @Enumerated(EnumType.STRING)
  private GeneralStatus status = GeneralStatus.ACTIVE;
  @Setter
  @ManyToOne
  @JoinColumn(name = "BOOK_ID")
  private Book book;
  @Convert(converter = BooleanConverter.class) // Y/N <-> true/false
  private boolean isReviewed = false;

  @Builder
  public Wish(Member member, LocalDate dueDate, Book book) {
    this.member = member;
    this.dueDate = dueDate;
    this.book = book;
  }

  public void setMember(Member member) {
    if (this.member != null) {
      throw new BusinessLogicException(ExceptionCode.MEMBER_CANNOT_BE_CHANGED);
    }
    this.member = member;
  }

  public void changedToReviewed() {
    this.isReviewed = true;
  }
}
