package com.kh.bookJeokBookJeok.review.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String title;
    private String writing;
    @OneToOne
    @JoinColumn(name = "WISHLIST_ID")
    private Wish wish;
}
