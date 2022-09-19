package com.kh.bookJeokBookJeok.like.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import com.kh.bookJeokBookJeok.board.entity.Board;
import com.kh.bookJeokBookJeok.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "LIKES")
@Getter
@Setter
public class Like extends BaseEntity {
    @Id
    private Long likeId;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;
}
