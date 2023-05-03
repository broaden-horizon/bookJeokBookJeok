package com.kh.bookJeokBookJeok.review;

import com.google.gson.Gson;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.repository.BookRepository;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import com.kh.bookJeokBookJeok.helper.RandomGenerator;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.review.entity.Review;
import com.kh.bookJeokBookJeok.review.repository.ReviewRepository;
import com.kh.bookJeokBookJeok.stub.MockDto;
import com.kh.bookJeokBookJeok.stub.MockEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTest {
  private final String email = "user@naver.com";
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ReviewRepository reviewRepository;
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private Gson gson;
  private Member member;

  @BeforeAll
  void init() {
    member = memberRepository.save(MockEntity.getMember(email, "paul"));
  }

  @Test
  @WithUserDetails(value = email, setupBefore = TestExecutionEvent.TEST_EXECUTION)
  void postReviewTest() throws Exception {
    // given
    String isbn = "1234";
    ReviewDto.Post post = MockDto.Review.getPost(isbn);
    String content = gson.toJson(post);

    // when
    ResultActions resultActions = mockMvc.perform(
        post("/reviews")
            .content(content)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.title").value(post.getTitle()))
        .andExpect(jsonPath("$.data.book.isbn").value(isbn));
  }

  @Test
  @WithUserDetails(value = email, setupBefore = TestExecutionEvent.TEST_EXECUTION)
  void getReviewTest() throws Exception {
    // given
    Review review = saveReview(member);

    // when
    ResultActions resultActions = mockMvc.perform(
        get("/reviews/" + review.getReviewId())
            .accept(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.title").value(review.getTitle()));
  }

  @Test
  @DisplayName("getReivew 예외 테스트 - 없는 리뷰인 경우")
  @WithUserDetails(value = email, setupBefore = TestExecutionEvent.TEST_EXECUTION)
  void getReviewTest_Exception() throws Exception {
    // given
    Review review = saveReview(member);
    Long wrongReviewId = 333L;

    // when
    ResultActions resultActions = mockMvc.perform(
        get("/reviews/" + wrongReviewId)
            .accept(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errors[0].reason").value(ExceptionCode.REVIEW_NOT_FOUND.getDescription()));
  }

  @Test
  @DisplayName("getReivew 예외 테스트 - 내가 작성한 리뷰가 아닌 경우")
  @WithUserDetails(value = email, setupBefore = TestExecutionEvent.TEST_EXECUTION)
  void getReviewTest_Exception2() throws Exception {
    // given
    Member wrongMember = saveMember();
    Review review = saveReview(wrongMember);

    // when
    ResultActions resultActions = mockMvc.perform(
        get("/reviews/" + review.getReviewId())
            .accept(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.errors[0].reason").value(ExceptionCode.NO_ACCESS_TO_REVIEW.getDescription()));
  }
  @Test
  @DisplayName("getReviews Test")
  @WithUserDetails(value = email, setupBefore = TestExecutionEvent.TEST_EXECUTION)
  void getReviewsTest() throws Exception{
    // given
    List<Review> reviews = saveReviews(member);
    int size = 5;

    // when
    ResultActions resultActions = mockMvc.perform(
        get("/reviews")
            .param("page", "1")
            .param("size", String.valueOf(size))
            .accept(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$..data.length()").value(size));
  }

  private List<Review> saveReviews(Member member) {
    List<Review> reviews = new ArrayList<>();
    for (int i= 0; i < 10; i++) {
      reviews.add(saveReview(member));
    }
    return reviews;
  }

  private Review saveReview(Member member) {
    Book book = bookRepository.save(MockEntity.getBook(RandomGenerator.randomStringGenerator()));
    Review review = reviewRepository.save(MockEntity.getReview(member, book));
    return review;
  }

  private Member saveMember() {
    return memberRepository.save(MockEntity.getMember());
  }
}
