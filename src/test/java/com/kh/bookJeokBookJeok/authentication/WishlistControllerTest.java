package com.kh.bookJeokBookJeok.authentication;

import com.google.gson.Gson;
import com.kh.bookJeokBookJeok.wishlist.dto.WishlistDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WishlistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void postMemberTest() throws Exception {
        // given
        WishlistDto.Post post = new WishlistDto.Post(
                "2000-00-00",
                "Y",
                "01023");
        String content = gson.toJson(post); //

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/wishlist")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
