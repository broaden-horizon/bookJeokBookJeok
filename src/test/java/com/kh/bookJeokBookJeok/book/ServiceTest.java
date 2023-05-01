package com.kh.bookJeokBookJeok.book;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.repository.BookRepository;
import com.kh.bookJeokBookJeok.book.service.BookService;
import com.kh.bookJeokBookJeok.stub.MockEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ServiceTest {
  @Autowired
  private BookService bookService;
  @Autowired
  private BookRepository bookRepository;

  @DisplayName("저장 테스트 : 동일 ISBN이 저장되어있지 않은 경우")
  @Test
  public void BookSaveTest() {
    // given
    String isbn = "1234";
    Book book = MockEntity.getBook(isbn);
    // when
    book = bookService.createIfAbsent(book);
    // then
    assertThat(book.getBookId())
        .isNotNull();
  }

  @DisplayName("저장 테스트 : 동일 ISBN이 저장되어있는 경우")
  @Test
  public void BookSaveTest2() {
    // given
    String isbn = "1234";
    bookRepository.save(MockEntity.getBook(isbn));
    //when
    Book book = bookService.createIfAbsent(MockEntity.getBook(isbn));

    // then
    assertThat(bookRepository.findAll().size())
        .isEqualTo(1);
    assertThat(book.getIsbn())
        .isEqualTo(isbn);
  }
}
