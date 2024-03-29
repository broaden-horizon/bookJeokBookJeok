package com.kh.bookJeokBookJeok.board.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import com.kh.bookJeokBookJeok.comment.entity.Comment;
import com.kh.bookJeokBookJeok.like.entity.Like;
import com.kh.bookJeokBookJeok.status.GeneralStatus;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    @OneToOne(targetEntity = Wish.class)
    @JoinColumn(name = "WISHLIST_ID")
    private Wish wish;
    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "board")
    private List<Like> likes = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private GeneralStatus status = GeneralStatus.ACTIVE;
}
