package com.qwon.eat_together.controller;

import com.qwon.eat_together.config.AuthUser;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.dto.MeetingDto;
import com.qwon.eat_together.repository.MeetingRepository;
import com.qwon.eat_together.service.MeetingService;
import com.qwon.eat_together.validation.MeetingDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;
    private final MeetingDtoValidator meetingDtoValidator;
    private final MeetingRepository meetingRepository;

    @InitBinder("meetingDto")// @Valid 검증 전에 실행
    public void meetingDtoInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(meetingDtoValidator);
    }

    @GetMapping("/meeting")
    public String newMeeting(@AuthUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(new MeetingDto());
        return "meeting/form";
    }

    @PostMapping("/meeting")
    public String newMeetingSubmit(@AuthUser Account account, @Valid MeetingDto meetingDto,
                                   Meeting meeting, Errors errors, Model model){

        if(errors.hasErrors()){
            model.addAttribute(account);
            return "meeting/form";
        }

        Meeting newMeeting = meetingService.createMeeting(meetingDto, meeting, account);
        return "redirect:/meeting/"+ URLEncoder.encode(newMeeting.getUrl(), StandardCharsets.UTF_8);
    }

    @GetMapping("/meeting/{url}")
    public String viewMeeting(@AuthUser Account account, @PathVariable String url, Model model){
        model.addAttribute(account);
        model.addAttribute(meetingRepository.findByUrl(url));
        return "meeting/view";
    }

    @GetMapping("/meeting/{url}/members")
    public String viewMeetingMembers(@AuthUser Account account, @PathVariable String url, Model model){
        model.addAttribute(account);
        model.addAttribute(meetingRepository.findByUrl(url));
        return "meeting/members";
    }

    @GetMapping("/meeting/{url}/join")
    public String joinMeeting(@AuthUser Account account, @PathVariable String url){

        Meeting meeting = meetingRepository.findMeetingWithManagersByUrl(url);
        meetingService.addMember(meeting,account);
        return "redirect:/meeting/"+URLEncoder.encode(url, StandardCharsets.UTF_8)+"/members";
    }

    @GetMapping("/meeting/{url}/leave")
    public String leaveMeeting(@AuthUser Account account, @PathVariable String url){

        Meeting meeting = meetingRepository.findMeetingWithManagersByUrl(url);
        meetingService.removeMember(meeting,account);
        return "redirect:/meeting/"+URLEncoder.encode(url, StandardCharsets.UTF_8)+"/members";

    }

}
