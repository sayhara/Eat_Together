package com.qwon.eat_together.controller;

import com.qwon.eat_together.dto.SignUpDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

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

        return "redirect:/";
    }
}
