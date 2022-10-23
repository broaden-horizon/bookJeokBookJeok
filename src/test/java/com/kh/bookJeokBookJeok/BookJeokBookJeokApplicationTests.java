package com.kh.bookJeokBookJeok;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.bookSearch.service.BookSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class BookJeokBookJeokApplicationTests {
	@Autowired
	private BookSearchService bookSearchService;
//	@Test
//	public void searchOne() {
//		//given
//			String input = "1234";
//		//when
//			BookSearchResponseDto.Item actual = bookSearchService.searchIsbn(input);
//		//then
//		System.out.println(actual);
//	}

}
