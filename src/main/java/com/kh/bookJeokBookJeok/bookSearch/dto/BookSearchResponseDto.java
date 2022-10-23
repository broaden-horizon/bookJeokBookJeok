package com.kh.bookJeokBookJeok.bookSearch.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class BookSearchResponseDto {
//    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<Item> items;

    @Getter
    @Setter
    @ToString
    public static class Item {
       private String title;
       private String link;
       private String image;
       private String author;
//       private Integer discount;
       private String publisher;
       private String isbn;
       private String description;
       private String pubdate;
    }
}
