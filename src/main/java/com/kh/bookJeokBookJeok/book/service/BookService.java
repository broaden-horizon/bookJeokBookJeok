package com.kh.bookJeokBookJeok.book.service;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.repository.BookRepository;
import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        checkIsbnExist(book.getIsbn());
        return bookRepository.save(book);
    }

    public Book addNumOfWishes(long bookId) {
        Book bookFound = findVerifiedBook(bookId);
        bookFound.addNumberOfWishes();
        return bookFound;
    }

    public Book findBook(long bookId) {
        Book bookFound = findVerifiedBook(bookId);
        return bookFound;
    }

    public Page<Book> findBooks(int page, int size) {
        Page<Book> books = bookRepository.findAll(PageRequest.of(page, size, Sort.by("bookId").descending()));
        return books;
    }
    public void checkIsbnExist(String isbn) {
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        if(optionalBook.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.BOOK_WITH_ISBN_EXISTS);
        }
    }

    public Book findVerifiedBook(long bookId) {
        Optional<Book> optionalBook =bookRepository.findById(bookId);
        Book bookFound = optionalBook.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.BOOK_NOT_FOUND));
        return bookFound;
    }
}
