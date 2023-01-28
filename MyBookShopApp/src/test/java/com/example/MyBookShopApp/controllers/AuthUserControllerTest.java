package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.security.BookstoreUserRegister;
import com.example.MyBookShopApp.services.TokenBlackListService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class AuthUserControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private BookstoreUserRegister userRegisterMock;

    @MockBean
    private TokenBlackListService tokenBlackListService;

    @Autowired
    public AuthUserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void handleUserregistrationTest() throws Exception {
        mockMvc.perform(post("/reg"))
                .andDo(print())
                .andExpect(content().string(containsString("Регистрация прошла успешно!")))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("tayshibaevtb@gmail.com")
    public void correctLoginTest() throws Exception {
        mockMvc.perform(logout("/logout"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logoutBL"));
    }

    @Test
    @WithUserDetails("tayshibaevtb@gmail.com")
    public void correctLogoutTest() throws Exception {
        mockMvc.perform(logout("/logout"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logoutBL"));
    }

    @Test
    public void correctLogoutBlTest() throws Exception {
        mockMvc.perform(get("/logoutBL"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signin"));
    }




}