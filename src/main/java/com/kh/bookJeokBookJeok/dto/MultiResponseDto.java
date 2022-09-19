package com.kh.bookJeokBookJeok.dto;

import com.kh.bookJeokBookJeok.book.dto.BookResponseDto;
import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MultiResponseDto<T> {
    private List<T> data;
    private PageInfo pageInfo;

    public MultiResponseDto(Page<T> page) {
        this.data = page.getContent();
        this.pageInfo = new PageInfo();
        pageInfo.setPage(page.getNumber());
        pageInfo.setTotalPages(page.getTotalPages());
        pageInfo.setSize(page.getSize());
        pageInfo.setTotalElements(page.getTotalElements());
    }

    public MultiResponseDto(List<T> data, BookSearchResponseDto response) {
        this.data = data;
        this.pageInfo = new PageInfo();
        pageInfo.setPage(response.getStart() / response.getDisplay() + 1);
        pageInfo.setTotalPages(response.getTotal() / response.getDisplay() + 1);
        pageInfo.setSize(response.getDisplay());
        pageInfo.setTotalElements(response.getTotal());
    }
}
