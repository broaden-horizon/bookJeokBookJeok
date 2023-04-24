package com.kh.bookJeokBookJeok.review.repository;

import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByWishlist(Wish wish);

}
