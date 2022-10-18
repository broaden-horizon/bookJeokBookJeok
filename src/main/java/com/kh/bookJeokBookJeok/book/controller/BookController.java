package com.kh.bookJeokBookJeok.book.controller;

import com.kh.bookJeokBookJeok.book.dto.BookDto;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.mapper.BookMapper;
import com.kh.bookJeokBookJeok.book.service.BookService;
import com.kh.bookJeokBookJeok.dto.MultiResponseDto;
import com.kh.bookJeokBookJeok.dto.SingleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {
    private BookService bookService;
    private BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }
    @PostMapping
    public ResponseEntity postBook(@Valid @RequestBody BookDto.Post bookPostDto) {
        Book book =  bookMapper.bookPostDtoToBook(bookPostDto);
        return new ResponseEntity(
                new SingleResponseDto<>(bookMapper.BookToBookResponseDto(bookService.createBook(book))),
                HttpStatus.CREATED);
    }
    @GetMapping("/{bookId}")
    public ResponseEntity getBook(@PathVariable @Min(1) long bookId) {
        Book book = bookService.findBook(bookId);
        return new ResponseEntity(
                new SingleResponseDto<>(bookMapper.BookToBookResponseDto(book)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getBooks(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size) {
        Page<Book> booksPage = bookService.findBooks(page, size);
        List<BookDto.BookResponseDto> books = bookMapper.BooksToBookResponses(booksPage.getContent());
        return new ResponseEntity(
                new MultiResponseDto(books, booksPage), HttpStatus.OK);
    }


}
