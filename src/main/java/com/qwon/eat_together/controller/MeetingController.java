package com.qwon.eat_together.controller;

import com.qwon.eat_together.config.AuthUser;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.dto.MeetingDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeetingController {

    @GetMapping("/meeting")
    public String newMeeting(@AuthUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(new MeetingDto());
        return "meeting/form";
    }
}
