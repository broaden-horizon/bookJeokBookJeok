package com.kh.bookJeokBookJeok.review.repository;

import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  Page<Review> findByMember(Member member, Pageable pageable);

  @Query("select r from Review r join r.member m where r.reviewId = ?1 and m.memberId = ?2")
  Optional<Review> findByReviewIdAndMember(Long reviewId, Long memberId);
}
