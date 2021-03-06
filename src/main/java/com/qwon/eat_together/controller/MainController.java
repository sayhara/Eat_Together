package com.qwon.eat_together.controller;

import com.qwon.eat_together.config.AuthUser;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.repository.AlarmRepository;
import com.qwon.eat_together.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MeetingRepository meetingRepository;
    private final AlarmRepository alarmRepository;

    @GetMapping("/")
    public String home(@AuthUser Account account, Model model){

        if(account!=null){ // anonymousUser 라면 null
            model.addAttribute(account);
        }

        long count=alarmRepository.countByAccountAndChecked(account,false);
        List<Meeting> meetingList=meetingRepository.findFirst9ByPublishedAndClosedOrderByPublishTimeDesc(true,false);
        model.addAttribute("meetingList",meetingList);
        model.addAttribute("hasAlarm",count>0);

        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/search/meeting")
    public String searchMeeting(String keyword, Model model,
                                @PageableDefault(size = 9, sort = "publishTime", direction = Sort.Direction.ASC)
                                        Pageable pageable){
        Page<Meeting> meetingList = meetingRepository.findByKeyword(keyword,pageable);
        model.addAttribute("meetingList",meetingList);
        model.addAttribute("keyword",keyword);
        return "search";
    }
}
