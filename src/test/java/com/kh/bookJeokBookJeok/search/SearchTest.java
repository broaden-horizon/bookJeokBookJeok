package com.kh.bookJeokBookJeok.search;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.bookSearch.service.BookSearchService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@SpringBootTest
public class SearchTest {
    @Autowired
    BookSearchService bookSearchService;
    @Test
    public void search() {
        //given
        String query = "클린코드";
        int page = 1;
        int size = 10;

        //when
        BookSearchResponseDto response = bookSearchService.search(query, page, size);
        int actual = response.getItems().size();
        //then
        assertThat(actual, is(equalTo(10)));
        System.out.println(response);

    }


}
