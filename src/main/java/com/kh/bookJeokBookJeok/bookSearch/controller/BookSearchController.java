package com.kh.bookJeokBookJeok.bookSearch.controller;

import com.kh.bookJeokBookJeok.bookSearch.dto.BookSearchResponseDto;
import com.kh.bookJeokBookJeok.bookSearch.service.BookSearchService;
import com.kh.bookJeokBookJeok.dto.MultiResponseDto;
import com.kh.bookJeokBookJeok.dto.SingleResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/book-search")
public class BookSearchController {
    BookSearchService bookSearchService;
    @GetMapping
    public ResponseEntity search(@RequestParam String query,
                                 @RequestParam int page, //page is started from 1
                                 @RequestParam int size) {
        BookSearchResponseDto response = bookSearchService.search(query, page, size);
        return new ResponseEntity<>(
                new MultiResponseDto<>(response.getItems(), response), HttpStatus.OK);
    }
}
