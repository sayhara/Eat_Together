package com.qwon.eat_together.controller;

import com.qwon.eat_together.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired // 기본적으로 테스트코드에는 생성자 주입을 위한 어노테이션의 설정파일 x
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("SignUp Test")
    @Test
    void signUp() throws Exception{
        mockMvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"));
    }
    
    @DisplayName("SignUp Test - input error")
    @Test
    void signUp_wrong_input() throws Exception{
        mockMvc.perform(post("/sign-up")
                .param("username","gyuwon")
                .param("email","invalidEmailForm")
                .param("password","12345678")
                .param("passwordCheck","12345678")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"));
    }

    @DisplayName("SignUp Test - input correct")
    @Test
    void signUp_correct_input() throws Exception{
        mockMvc.perform(post("/sign-up")
                .param("username","gyuwon")
                .param("email","email@naver.com")
                .param("password","12345678")
                .param("passwordCheck","12345678")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        assertTrue(accountRepository.existsByEmail("email@naver.com"));
    }

}