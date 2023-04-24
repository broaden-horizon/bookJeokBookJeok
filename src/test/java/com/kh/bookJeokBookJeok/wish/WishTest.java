package com.kh.bookJeokBookJeok.wish;

import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.review.repository.ReviewRepository;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import com.kh.bookJeokBookJeok.wish.repository.WishlistRepository;
import com.kh.bookJeokBookJeok.wish.service.WishlistService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WishTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    private Wish wishResponse;
    private Review reviewResponse;

    @BeforeAll
    public void createEntity() {
        Member member = new Member();
        member.setEmail("abc@naver.com");
        member.setNickname("test");
        member.setPassword("test");

        memberRepository.save(member);

        Wish wish = new Wish();
        wish.setNotice(true);
        wish.setMember(member);
        wish.setDueDate(LocalDate.now());
        wish.setIsbn("9788966260959");

        wishResponse = wishlistRepository.save(wish);

        Review review = new Review();
        review.setWish(wish);
        review.setWriting("test");
        review.setTitle("test");

        reviewResponse = reviewRepository.save(review);
    }
    @Test
    public void getTest() {
        //given

        //when
        Wish actual = wishlistRepository.findById(wishResponse.getWishlistId()).get();
        //then
        assertThat(actual.getReview().getTitle(), is(equalTo("test")));
    }

    @Test
    public void retrieveTest() {
        Review review = reviewRepository.findById(reviewResponse.getReviewId()).get();

        assertThat(review.getWish().getIsbn(), is(equalTo("1234")));
    }
}
