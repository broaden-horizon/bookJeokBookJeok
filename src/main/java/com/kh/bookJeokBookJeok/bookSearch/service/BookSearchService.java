package com.kh.bookJeokBookJeok.bookSearch.service;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;

@Service
public class BookSearchService {
    @Value("${naver.search.id}")
    private String naverClientId;

    @Value("${naver.search.secret}")
    private String naverClientSecret;
    private final String URI = "https://openapi.naver.com/v1/search/book.json";
    public BookSearchResponseDto.Item searchWithIsbn(String isbn) {
        BookSearchResponseDto responseDto = search(isbn, 1, 1);
        BookSearchResponseDto.Item item = responseDto.getItems().get(0);
        if(!item.getIsbn().equals(isbn)) {
            throw new BusinessLogicException(ExceptionCode.BOOK_NOT_FOUND_WITH_ISBN);
        }
        return item;
    }
    public BookSearchResponseDto search(String query, int page, int size) {
        int start = calculateStart(page, size);
        //인코딩 기능을 추가한 URI 빌더
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(URI);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);

        //WebClient 인스턴스 생성
        WebClient client = WebClient.builder()
                .uriBuilderFactory(factory)
                .baseUrl(URI)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-Naver-Client-Id", naverClientId)
                .defaultHeader("X-Naver-Client-Secret", naverClientSecret)
                .build();

        BookSearchResponseDto response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("display", size)
                        .queryParam("start", start)
                        .build()
                )
                .retrieve()
                .bodyToMono(BookSearchResponseDto.class)
                .block(); //nonblock으로도 해보자

        //검색결과가 없는 경우 익셉션 발생
        if(response.getDisplay() == 0) {
            throw new BusinessLogicException(ExceptionCode.BOOK_NOT_FOUND);
        }
        return response;
    }
    int calculateStart(int page, int size) { //page is started from 1
        return (page - 1) * size + 1;
    }
}
