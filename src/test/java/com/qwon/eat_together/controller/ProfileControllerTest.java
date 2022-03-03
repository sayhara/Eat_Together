package com.qwon.eat_together.controller;

import com.qwon.eat_together.domain.Account;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProfileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void beforeEach(){
        SignUpDto signUpDto=new SignUpDto();
        signUpDto.setUsername("gyuwon");
        signUpDto.setEmail("qwon@gmail.com");
        signUpDto.setPassword("123456789");
        signUpDto.setPasswordCheck("123456789");
        accountService.join(signUpDto);
    }

    @AfterEach
    public void afterEach(){
        accountRepository.deleteAll();
    }

    @WithUserDetails(value = "gyuwon", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("profileUpdate Test - input success")
    @Test
    public void updateProfile_ok() throws Exception{

        String bio="less than 30 words";
        mockMvc.perform(post("/settings/profile")
                .param("bio",bio)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/settings/profile"))
                .andExpect(flash().attributeExists("message"));

        Account gyuwon = accountRepository.findByUsername("gyuwon");
        assertEquals(bio,gyuwon.getBio());
    }

    @WithUserDetails(value = "gyuwon", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("profileUpdate Test - input error")
    @Test
    public void updateProfile_error() throws Exception{

        String bio="more than 30 words more than 30 words more than 30 words more than 30 words more than 30 words";
        mockMvc.perform(post("/settings/profile")
                .param("bio",bio)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("settings/profile"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().hasErrors());

        Account gyuwon = accountRepository.findByUsername("gyuwon");
        assertNull(gyuwon.getBio());
    }

    @WithUserDetails(value = "gyuwon", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("passwordUpdate Test - input success")
    @Test
    public void updatePassword_ok() throws Exception{

        String newPassword="{noop}"+"12345678";
        String newPasswordRepeat="{noop}"+"12345678";

        mockMvc.perform(post("/settings/password")
                .param("password",newPassword)
                .param("passwordCheck",newPasswordRepeat)
                .with(csrf()))
                .andExpect(status().is3xxRedirection()) // 상태값은 완료되면 redirect
                .andExpect(redirectedUrl("/settings/password"))
                .andExpect(flash().attributeExists("message"));

        Account gyuwon = accountRepository.findByUsername("gyuwon");
        assertTrue(passwordEncoder.matches("12345678",gyuwon.getPassword()));

    }

    @WithUserDetails(value = "gyuwon", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("passwordUpdate Test - input error")
    @Test
    public void updatePassword_error() throws Exception{

        String newPassword="{noop}"+"12345678";
        String newPasswordRepeat="{noop}"+"87654321";

        mockMvc.perform(post("/settings/password")
                .param("password",newPassword)
                .param("passwordCheck",newPasswordRepeat)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("settings/password"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("passwordDto"))
                .andExpect(model().hasErrors());
    }

}