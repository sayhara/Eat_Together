package com.qwon.eat_together.controller;

import com.qwon.eat_together.dto.SignUpDto;
import com.qwon.eat_together.repository.AccountRepository;
import com.qwon.eat_together.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void beforeEach(){
        SignUpDto signUpDto=new SignUpDto();
        signUpDto.setUsername("gyuwon");
        signUpDto.setEmail("qwon@gmail.com");
        signUpDto.setPassword("12345678");
        signUpDto.setPasswordCheck("12345678");
        accountService.join(signUpDto);
    }

    @AfterEach
    void afterEach(){
        accountRepository.deleteAll(); // 데이터의 중복방지를 위해 refresh
    }

    @DisplayName("Login Test - success")
    @Test
    void login_success() throws Exception{

        mockMvc.perform(post("/login")
            .param("username","gyuwon")
            .param("password","12345678")
            .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
//              .andExpect(view().name("redirect:/")) // view가 호출된게 아님
                .andExpect(authenticated().withUsername("gyuwon"));
    }

    @DisplayName("Login Test - error")
    @Test
    void login_error() throws Exception{
        mockMvc.perform(post("/login")
            .param("username","9yuwon")
            .param("password","12345678")
            .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @DisplayName("Logout")
    @Test
    void logout() throws Exception{
        mockMvc.perform(post("/logout")
            .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }



}