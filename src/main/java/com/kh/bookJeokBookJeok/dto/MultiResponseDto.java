package com.kh.bookJeokBookJeok.dto;

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
}
