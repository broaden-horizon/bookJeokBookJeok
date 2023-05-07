package com.kh.bookJeokBookJeok.wish;

import com.google.gson.Gson;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.repository.BookRepository;
import com.kh.bookJeokBookJeok.helper.RandomGenerator;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import com.kh.bookJeokBookJeok.status.GeneralStatus;
import com.kh.bookJeokBookJeok.stub.MockDto;
import com.kh.bookJeokBookJeok.stub.MockEntity;
import com.kh.bookJeokBookJeok.wish.dto.WishDto;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import com.kh.bookJeokBookJeok.wish.repository.WishRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
  private WishRepository wishRepository;
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
  void postTest() throws Exception {
    // given
    String isbn = "1234";
    WishDto.Post post = MockDto.Wish.getPost(isbn);
    String content = gson.toJson(post);
    content = content = changeDueDateFormat(content, post.getDueDate().toString());

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

  @Test
  @WithUserDetails(value = email, setupBefore = TestExecutionEvent.TEST_EXECUTION)
  void patchTest() throws Exception {
    // given
    WishDto.Patch patch = MockDto.Wish.getPatch();
    String content = gson.toJson(patch);
    content = changeDueDateFormat(content, patch.getDueDate().toString());
    Wish wish = saveWish();

    // when
    ResultActions resultActions = mockMvc.perform(
        patch("/wishes/" + wish.getWishId())
            .content(content)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.dueDate").value(patch.getDueDate().toString()));

  }

  @Test
  @WithUserDetails(value = email, setupBefore = TestExecutionEvent.TEST_EXECUTION)
  void getWishTest() throws Exception {
    // given
    Wish wish = saveWish();

    // when
    ResultActions resultActions = mockMvc.perform(
        get("/wishes/" + wish.getWishId())
            .accept(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.book.isbn").value(wish.getBook().getIsbn()));
  }

  @Test
  @WithUserDetails(value = email, setupBefore = TestExecutionEvent.TEST_EXECUTION)
  void getWishesTest() throws Exception {
    // given
    int size = 5;
    List<Wish> wishes = saveWishes(size);

    // when
    ResultActions resultActions = mockMvc.perform(
        get("/wishes")
            .param("page", "1")
            .param("size", String.valueOf(size))
            .accept(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$..data.length()").value(size));
  }

  @Test
  @WithUserDetails(value = email, setupBefore = TestExecutionEvent.TEST_EXECUTION)
  void deleteTest() throws Exception {
    // given
    Wish wish = saveWish();

    // when
    ResultActions resultActions = mockMvc.perform(
        delete("/wishes/" + wish.getWishId())
            .accept(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isOk());

    Wish wishAfter = wishRepository.findById(wish.getWishId()).get();
    assertThat(wishAfter.getStatus())
        .isSameAs(GeneralStatus.DELETED);
  }

  private String changeDueDateFormat(String content, String changeTo) {
    String regex = "(\"dueDate\":)(\\{.*?\\})";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(content);
    return matcher.replaceFirst("$1\"" + changeTo + "\"");
  }

  private List<Wish> saveWishes(int size) {
    List<Wish> wishes = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      wishes.add(saveWish());
    }
    return wishes;
  }

  private Wish saveWish() {
    Book book = bookRepository.save(MockEntity.getBook(RandomGenerator.randomStringGenerator()));
    Wish wish = wishRepository.save(MockEntity.getWish(member, book));
    return wish;
  }
}
