package com.kh.bookJeokBookJeok.wish;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.repository.BookRepository;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import com.kh.bookJeokBookJeok.stub.MockEntity;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import com.kh.bookJeokBookJeok.wish.repository.WishRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class RepositoryTest {
  @Autowired
  private WishRepository wishRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private BookRepository bookRepository;

  @Test
  @DisplayName("findByMemberAndBookTest")
  void findByMemberAndBookTest() {
    // given
    String isbn = "1234";
    Book book = bookRepository.save(MockEntity.getBook(isbn));
    Member member = memberRepository.save(MockEntity.getMember());
    wishRepository.save(MockEntity.getWish(member, book));

    // when
    Optional<Wish> optionalWish = wishRepository.findByMemberAndBook(member, book);

    // then
    assertThat(optionalWish.isPresent())
        .isTrue();
  }
}
