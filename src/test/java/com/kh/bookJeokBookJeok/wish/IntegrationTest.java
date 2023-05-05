package com.kh.bookJeokBookJeok.wish;

import com.google.gson.Gson;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.repository.BookRepository;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import com.kh.bookJeokBookJeok.stub.MockDto;
import com.kh.bookJeokBookJeok.stub.MockEntity;
import com.kh.bookJeokBookJeok.wish.dto.WishDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
  void a() {
    String isbn = "1234";
    WishDto.Post post = MockDto.Wish.getPost(isbn);
    String content = gson.toJson(post);
    System.out.println(content);
    String regex = "(\"dueDate\":)(\\{.*?\\})";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(content);
    content = matcher.replaceFirst("$1\"2023-05-05\"");
    System.out.println(content);
  }
  @Test
  @WithUserDetails(value = email, setupBefore = TestExecutionEvent.TEST_EXECUTION)
  void postTest() throws Exception {
    // given
    String isbn = "1234";
    WishDto.Post post = MockDto.Wish.getPost(isbn);
    String content = gson.toJson(post);
    System.out.println(content);
    String regex = "(\"dueDate\":)(\\{.*?\\})";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(content);
    content = matcher.replaceFirst("$1\"2023-05-05\"");

    // when
    ResultActions resultActions = mockMvc.perform(
        post("/wishes")
            .content(content)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.book.isbn").value(isbn));

    Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
    assertThat(optionalBook.isPresent())
        .isTrue();
  }
}
