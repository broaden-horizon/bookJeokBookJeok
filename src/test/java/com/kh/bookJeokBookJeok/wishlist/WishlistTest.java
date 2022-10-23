package com.kh.bookJeokBookJeok.wishlist;

import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.review.repository.ReviewRepository;
import com.kh.bookJeokBookJeok.wishlist.entity.Wishlist;
import com.kh.bookJeokBookJeok.wishlist.repository.WishlistRepository;
import com.kh.bookJeokBookJeok.wishlist.service.WishlistService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WishlistTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    private Wishlist wishlistResponse;
    private Review reviewResponse;

    @BeforeAll
    public void createEntity() {
        Member member = new Member();
        member.setEmail("abc@naver.com");
        member.setNickname("test");
        member.setPassword("test");

        memberRepository.save(member);

        Wishlist wishlist = new Wishlist();
        wishlist.setNotice(true);
        wishlist.setMember(member);
        wishlist.setDueDate(LocalDate.now());
        wishlist.setIsbn("9788966260959");

        wishlistResponse = wishlistRepository.save(wishlist);

        Review review = new Review();
        review.setWishlist(wishlist);
        review.setWriting("test");
        review.setTitle("test");

        reviewResponse = reviewRepository.save(review);
    }
    @Test
    public void getTest() {
        //given

        //when
        Wishlist actual = wishlistRepository.findById(wishlistResponse.getWishlistId()).get();
        //then
        assertThat(actual.getReview().getTitle(), is(equalTo("test")));
    }

    @Test
    public void retrieveTest() {
        Review review = reviewRepository.findById(reviewResponse.getReviewId()).get();

        assertThat(review.getWishlist().getIsbn(), is(equalTo("1234")));
    }
}
