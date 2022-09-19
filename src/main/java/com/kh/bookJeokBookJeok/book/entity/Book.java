package com.kh.bookJeokBookJeok.book.entity;

import com.kh.bookJeokBookJeok.audit.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Getter
@Setter
public class Book extends BaseEntity {
    @Id
    private Long bookId;
    private int numberOfWishes = 0;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String description;
    private String image;

    public void addNumberOfWishes() {
        this.numberOfWishes++;
    }
}
