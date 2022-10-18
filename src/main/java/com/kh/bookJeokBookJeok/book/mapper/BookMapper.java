package com.kh.bookJeokBookJeok.book.mapper;

import com.kh.bookJeokBookJeok.book.dto.BookDto;
import com.kh.bookJeokBookJeok.book.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper{
    Book bookPostDtoToBook(BookDto.Post bookPostDto);
    BookDto.BookResponseDto BookToBookResponseDto(Book book);
    List<BookDto.BookResponseDto> BooksToBookResponses(List<Book> books);
}
