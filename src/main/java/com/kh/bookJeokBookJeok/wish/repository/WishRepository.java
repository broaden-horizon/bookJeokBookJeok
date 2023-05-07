package com.kh.bookJeokBookJeok.wish.repository;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
  Optional<Wish> findByMemberAndBook(Member member, Book book);

  Optional<Wish> findByWishIdAndMember(Long wishId, Member member);

  @Query("select w from Wish w join w.member m where w.wishId=?1 and m.memberId=?2")
  Optional<Wish> findByWishIdAndMemberId(Long wishId, Long memberId);

  @Query("select w from Wish w join w.member m where w.status='ACTIVE' and m.memberId=?1")
  Page<Wish> findAllByMemberId(Long memberId, Pageable pageable);
}
