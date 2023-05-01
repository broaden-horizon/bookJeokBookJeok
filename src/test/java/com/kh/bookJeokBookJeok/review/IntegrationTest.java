package com.kh.bookJeokBookJeok.review;

import com.google.gson.Gson;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import com.kh.bookJeokBookJeok.review.dto.ReviewDto;
import com.kh.bookJeokBookJeok.stub.MockDto;
import com.kh.bookJeokBookJeok.stub.MockEntity;
import org.junit.jupiter.api.BeforeAll;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class IntegrationTest {
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private Gson gson;
  private Member member;
  private final String email = "user@naver.com";

  @BeforeAll
  void init() {
    member =  memberRepository.save(MockEntity.getMember(email));
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
        post("/review")
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
}
