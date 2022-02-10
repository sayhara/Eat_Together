package com.qwon.eat_together.controller;

import com.qwon.eat_together.config.AuthUser;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.dto.SignUpDto;
import com.qwon.eat_together.repository.AccountRepository;
import com.qwon.eat_together.service.AccountService;
import com.qwon.eat_together.validation.SignUpDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpDtoValidator signUpDtoValidator;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @InitBinder("signUpDto")// @Valid 검증 전에 실행
    public void signUpDtoInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpDtoValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute("signUpDto",new SignUpDto());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Validated SignUpDto signUpDto, Errors errors){

        if(errors.hasErrors()){
            return "account/sign-up";
        }

        Account account = accountService.join(signUpDto);
        accountService.login(account);

        return "redirect:/";
    }

    @GetMapping("/profile/{username}")
    public String profilePage(@PathVariable String username, Model model, @AuthUser Account account){

        Account owner = accountService.getAccount(username);

        model.addAttribute(owner);
        model.addAttribute("isOwner",owner.equals(account));
        return "account/profile";
    }

}
