package com.kh.bookJeokBookJeok.book.service;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;

  /**
   * 같은 ISBN으로 저장된 Book record가 없을 때만 저장합니다.
   * @param book
   * @return Book
   */
  public Book createIfAbsent(Book book) {
    Optional<Book> optionalBook = bookRepository.findByIsbn(book.getIsbn());
    return optionalBook.orElseGet(() -> bookRepository.save(book));
  }
}
