package com.qwon.eat_together.controller;

import com.qwon.eat_together.config.AuthUser;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.dto.MeetingDescriptionDto;
import com.qwon.eat_together.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;

@Controller
@RequestMapping("/meeting/{url}/settings")
@RequiredArgsConstructor
public class SettingController {

    private final MeetingService meetingService;
    private final ModelMapper modelMapper;

    @GetMapping("/description")
    public String viewMeetingInfo(@AuthUser Account account, @PathVariable String url, Model model)
            throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);

        model.addAttribute(account);
        model.addAttribute(meeting);
        model.addAttribute(modelMapper.map(meeting,MeetingDescriptionDto.class));
        return "meeting/settings/description";
    }

    @PostMapping("/description")
    public String updateMeetingInfo(@AuthUser Account account, @PathVariable String url,
                                    MeetingDescriptionDto meetingDescriptionDto, Errors errors,
                                    Model model, RedirectAttributes attributes) throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);

        if(errors.hasErrors()){
            model.addAttribute(account);
            model.addAttribute(meeting);
            return "meeting/settings/description";
        }

        meetingService.updateMeetingDescription(meeting, meetingDescriptionDto);
        attributes.addFlashAttribute("message","모임 소개를 수정했습니다.");
        return "redirect:/meeting/"+ URLEncoder.encode(url, StandardCharsets.UTF_8)+"/settings/description";
    }
}
