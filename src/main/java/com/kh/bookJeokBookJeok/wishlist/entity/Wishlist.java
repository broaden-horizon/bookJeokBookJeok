package com.kh.bookJeokBookJeok.wishlist.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.status.GeneralStatus;
import com.kh.bookJeokBookJeok.util.converter.BooleanConverter;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class Wishlist extends BaseEntity {
    @Id
    private Long wishlistId;
    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    private String dueDate;
    @Convert(converter = BooleanConverter.class) // Y/N <-> true/false
    private boolean isNotice;
    @Convert(converter = BooleanConverter.class)
    private boolean isShared;
    @Column(nullable = false)
    private String title;
    private String writing;
    private GeneralStatus status = GeneralStatus.ACTIVE;
}
