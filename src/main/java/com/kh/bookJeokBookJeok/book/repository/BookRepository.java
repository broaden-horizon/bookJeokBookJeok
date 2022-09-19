package com.kh.bookJeokBookJeok.book.repository;


import com.kh.bookJeokBookJeok.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    public Optional<Book> findByIsbn(String isbn);
}
