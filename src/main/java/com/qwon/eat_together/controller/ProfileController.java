package com.qwon.eat_together.controller;

import com.qwon.eat_together.config.AuthUser;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Profile;
import com.qwon.eat_together.repository.AccountRepository;
import com.qwon.eat_together.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final AccountService accountService;

    @GetMapping("/settings/profile")
    public String SettingsProfile(@AuthUser Account account, Model model){

        model.addAttribute("account",account);
        model.addAttribute(new Profile(account)); // 생성자 주입
        return "settings/profile";
    }

    @PostMapping("/settings/profile")
    public String updateProfile(@AuthUser Account account, @Valid Profile profile, Errors errors,
                                Model model, RedirectAttributes attributes){

        if(errors.hasErrors()){
            model.addAttribute(account);
            return "settings/profile";
        }

        accountService.profileUpdate(account,profile);
        attributes.addFlashAttribute("message","프로필을 수정했습니다.");
        return "redirect:/settings/profile";
    }
}
