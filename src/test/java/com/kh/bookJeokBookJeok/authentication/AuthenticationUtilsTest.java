package com.kh.bookJeokBookJeok.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class AuthenticationUtilsTest {
    @Autowired
    private AuthenticationUtils authenticationUtils;
    @Test
    public void createRolesTest() {
        //when
        String input = "rlghd1698@naver.com";
        List<String> actual = authenticationUtils.createRoleByEmail(input);
        List<String> expected = authenticationUtils.getRolesForAdmin();
        assertThat(actual, is(equalTo(expected)));
    }
}
