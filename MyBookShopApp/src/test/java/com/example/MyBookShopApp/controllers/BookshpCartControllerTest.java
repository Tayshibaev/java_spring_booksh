package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.security.BookstoreUserRegister;
import com.example.MyBookShopApp.services.TokenBlackListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class BookshpCartControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    BookshpCartControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    private String slug = "123";

    @Test
    public void handleChangeBookCartStatusContentNullTest() throws Exception {
        MockHttpServletResponse resp = mockMvc.perform(post("/books/changeBookStatus/CART/" + slug))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + slug))
                .andReturn()
                .getResponse();
        Assertions.assertEquals(resp.getCookie("cartContents").getValue(), slug);
        Assertions.assertEquals(resp.getCookie("cartContents").getPath(), "/books");
    }

    @Test
    public void handleChangeBookCartStatusContentEmptyTest() throws Exception {
        Cookie cookie = new Cookie("cartContents", "");
        cookie.setPath("/books");
        MockHttpServletResponse resp = mockMvc.perform(post("/books/changeBookStatus/CART/" + slug)
                        .cookie(cookie))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + slug))
                .andReturn()
                .getResponse();
        Assertions.assertEquals(resp.getCookie("cartContents").getValue(), slug);
        Assertions.assertEquals(resp.getCookie("cartContents").getPath(), "/books");
    }

    @Test
    public void handleChangeBookCartStatusContentNotSlugTest() throws Exception {
        String slugOld = "0000";
        Cookie cookie = new Cookie("cartContents", slugOld);
        cookie.setPath("/books");
        MockHttpServletResponse resp = mockMvc.perform(post("/books/changeBookStatus/CART/" + slug)
                        .cookie(cookie))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + slug))
                .andReturn()
                .getResponse();
        Assertions.assertEquals(resp.getCookie("cartContents").getValue(), slugOld + "/" + slug);
        Assertions.assertEquals(resp.getCookie("cartContents").getPath(), "/books");
    }

    @Test
    public void handleChangeBookCartStatusContentEqualsSlugTest() throws Exception {
        Cookie cookie = new Cookie("cartContents", slug);
        cookie.setPath("/books");
        MockHttpServletResponse resp = mockMvc.perform(post("/books/changeBookStatus/CART/" + slug)
                        .cookie(cookie))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + slug))
                .andReturn()
                .getResponse();
        Assertions.assertEquals(resp.getCookies().length, 0);
    }


    @Test
    public void handleRemoveBookFromCartRequestRemoveSlugTest() throws Exception {
        String slugOld = "000";
        String slugOld2 = "0001";
        String slugTest = slugOld + "/" + slug + "/" + slugOld2;
        Cookie cookie = new Cookie("cartContents", slugTest);
        cookie.setPath("/books");
        MockHttpServletResponse resp = mockMvc.perform(post("/books/changeBookStatus/UNLINK/cart/remove/" + slug)
                        .cookie(cookie))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/cart"))
                .andReturn()
                .getResponse();
        Assertions.assertEquals(resp.getCookie("cartContents").getValue(), slugOld + "/" + slugOld2);
        Assertions.assertEquals(resp.getCookie("cartContents").getPath(), "/books");
    }

    @Test
    public void handleRemoveBookFromCartRequestContentIsEmptyTest() throws Exception {
        Cookie cookie = new Cookie("cartContents", "");
        cookie.setPath("/books");
        MockHttpServletResponse resp = mockMvc.perform(post("/books/changeBookStatus/UNLINK/cart/remove/" + slug)
                        .cookie(cookie))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/cart"))
                .andReturn()
                .getResponse();
        Assertions.assertEquals(resp.getCookies().length, 0);
    }

    @Test
    public void handleRemoveBookFromCartRequestContentIsNullTest() throws Exception {
        MockHttpServletResponse resp = mockMvc.perform(post("/books/changeBookStatus/UNLINK/cart/remove/" + slug))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/cart"))
                .andReturn()
                .getResponse();
        Assertions.assertEquals(resp.getCookies().length, 0);
    }
}