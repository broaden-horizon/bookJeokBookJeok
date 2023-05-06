package com.kh.bookJeokBookJeok.wish.repository;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
  Optional<Wish> findByMemberAndBook(Member member, Book book);

  Optional<Wish> findByWishIdAndMember(Long wishId, Member member);
}
