package com.kh.bookJeokBookJeok.wishlist.repository;

import com.kh.bookJeokBookJeok.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

}
