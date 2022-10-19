package com.kh.bookJeokBookJeok.wishlist.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.status.GeneralStatus;
import com.kh.bookJeokBookJeok.util.converter.BooleanConverter;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Wishlist extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wishlistId;
    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate dueDate;
    @Convert(converter = BooleanConverter.class) // Y/N <-> true/false
    private boolean isNotice;
    private GeneralStatus status = GeneralStatus.ACTIVE;
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "BOOK_ID")
//    private Book book;
    private String isbn;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;
}
