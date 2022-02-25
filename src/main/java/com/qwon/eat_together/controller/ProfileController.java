package com.qwon.eat_together.controller;

import com.qwon.eat_together.config.AuthUser;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.dto.AlarmDto;
import com.qwon.eat_together.dto.UsernameDto;
import com.qwon.eat_together.dto.Profile;
import com.qwon.eat_together.dto.PasswordDto;
import com.qwon.eat_together.service.AccountService;
import com.qwon.eat_together.validation.PasswordDtoValidator;
import com.qwon.eat_together.validation.UsernameDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final AccountService accountService;
    private final PasswordDtoValidator passwordDtoValidator;
    private final UsernameDtoValidator usernameDtoValidator;

    @InitBinder("passwordDto") // 검증할 장소
    public void passwordDtoInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(passwordDtoValidator);
    }

    @InitBinder("nicknameDto")
    public void nicknameDtoInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(usernameDtoValidator);
    }

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

    @GetMapping("/settings/password")
    public String SettingsPassword(@AuthUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(new PasswordDto());
        return "settings/password";
    }

    @PostMapping("/settings/password")
    public String updatePassword(@AuthUser Account account, Model model, @Valid PasswordDto passwordDto,
                                 Errors errors, RedirectAttributes attributes){

        if(errors.hasErrors()){
            model.addAttribute(account);
            return "settings/password";
        }

        model.addAttribute(account);
        accountService.passwordUpdate(account,passwordDto);
        attributes.addFlashAttribute("message","패스워드를 수정했습니다.");
        return "redirect:/settings/password";
    }

    @GetMapping("/settings/alarms")
    public String SettingsAlarms(@AuthUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(new AlarmDto(account));
        return "settings/alarms";
    }

    @PostMapping("/settings/alarms")
    public String updateAlarms(@AuthUser Account account, @Valid AlarmDto alarmDto, Errors errors,
                                     Model model, RedirectAttributes attributes){
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "settings/alarms";
        }
        accountService.updateAlarm(account,alarmDto);
        attributes.addFlashAttribute("message","알림 설정을 업데이트 했습니다.");
        return "redirect:/settings/alarms";
    }

    @GetMapping("/settings/username")
    public String SettingsUsername(@AuthUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(new UsernameDto());
        return "settings/username";
    }

    @PostMapping("/settings/username")
    public String updateUsername(@AuthUser Account account, @Valid UsernameDto usernameDto, Errors errors,
                                 Model model, RedirectAttributes attributes){
        if(errors.hasErrors()){
            model.addAttribute(account);
            return "settings/username";
        }

        accountService.updateUsername(account,usernameDto);
        attributes.addFlashAttribute("message","닉네임을 수정했습니다.");
        return "redirect:/settings/username";
    }

    @PostMapping("/meeting/remove")
    public String removeMeeting(@AuthUser Account account, HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        accountService.remove(account);
        if (auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
