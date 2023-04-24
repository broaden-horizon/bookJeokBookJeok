package com.kh.bookJeokBookJeok.wish.repository;

import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface WishlistRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByMemberAndIsbn(Member member, String isbn);
    Optional<Wish> findByMember(Member member);
}
