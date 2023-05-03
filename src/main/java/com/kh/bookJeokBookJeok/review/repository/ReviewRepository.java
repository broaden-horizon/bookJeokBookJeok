package com.kh.bookJeokBookJeok.review.repository;

import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  Page<Review> findByMember(Member member, Pageable pageable);
}
