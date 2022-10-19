package com.kh.bookJeokBookJeok.wishlist.repository;

import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByMemberAndIsbn(Member member, String isbn);
}
