package com.kh.bookJeokBookJeok.bookSearch.service;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
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
        return response;
    }
    int calculateStart(int page, int size) { //page is started from 1
        return (page - 1) * size + 1;
    }
}
