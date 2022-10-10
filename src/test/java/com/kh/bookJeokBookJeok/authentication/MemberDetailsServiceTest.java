package com.kh.bookJeokBookJeok.authentication;

import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class MemberDetailsServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    AuthenticationUtils authenticationUtils;
    @Autowired
    MemberDetailsService memberDetailsService;
    @BeforeEach
    public void createMember() {
        Member member = new Member();
        member.setEmail("rlghd1698@naver.com");
        member.setPassword("1234");
        member.setNickname("kkk");
        Member actual = memberService.createMember(member);
    }
    @Test
    public void loadUserTest() {
        String input = "rlghd1698@naver.com";
        UserDetails actual = memberDetailsService.loadUserByUsername(input);
        assertThat(actual.getUsername(), is(equalTo(input)));
    }
}
