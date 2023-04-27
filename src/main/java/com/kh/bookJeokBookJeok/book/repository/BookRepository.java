package com.kh.bookJeokBookJeok.book.repository;

import com.kh.bookJeokBookJeok.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
