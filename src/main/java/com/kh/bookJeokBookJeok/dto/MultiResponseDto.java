package com.kh.bookJeokBookJeok.dto;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Getter
public class MultiResponseDto<T> {
    private List<T> data;
    private PageInfo pageInfo;
    public MultiResponseDto(List<T> data, Page page) {
        this.data = data;
        //page는 1부터 시작
        this.pageInfo = new PageInfo(page.getNumber() + 1, page.getSize(), page.getTotalElements(), page.getTotalPages());
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
