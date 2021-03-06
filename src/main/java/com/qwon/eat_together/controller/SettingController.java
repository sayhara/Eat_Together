package com.qwon.eat_together.controller;

import com.qwon.eat_together.config.AuthUser;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.dto.MeetingInfoDto;
import com.qwon.eat_together.repository.MeetingRepository;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;

@Controller
@RequestMapping("/meeting/{url}/setting")
@RequiredArgsConstructor
public class SettingController {

    private final MeetingService meetingService;
    private final MeetingRepository meetingRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/info")
    public String viewMeetingInfo(@AuthUser Account account, @PathVariable String url, Model model)
            throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);

        model.addAttribute(account);
        model.addAttribute(meeting);
        model.addAttribute(modelMapper.map(meeting, MeetingInfoDto.class));
        return "meeting/setting/info";
    }

    @PostMapping("/info")
    public String updateMeetingInfo(@AuthUser Account account, @PathVariable String url,
                                    MeetingInfoDto meetingInfoDto, Errors errors,
                                    Model model, RedirectAttributes attributes) throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);

        if(errors.hasErrors()){
            model.addAttribute(account);
            model.addAttribute(meeting);
            return "meeting/setting/info";
        }

        meetingService.updateMeetingInfo(meeting, meetingInfoDto);
        attributes.addFlashAttribute("message","?????? ????????? ??????????????????.");
        return "redirect:/meeting/"+ URLEncoder.encode(url, StandardCharsets.UTF_8)+"/setting/info";
    }

    @GetMapping("/banner")
    public String viewMeetingBanner(@AuthUser Account account, @PathVariable String url, Model model)
            throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);
        model.addAttribute(account);
        model.addAttribute(meeting);

        return "meeting/setting/banner";
    }

    @PostMapping("/banner")
    public String updateMeetingBanner(@AuthUser Account account, @PathVariable String url,
                                      String image, RedirectAttributes attributes) throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);

        meetingService.updateMeetingImage(meeting,image);
        attributes.addFlashAttribute("message","?????? ?????????????????? ??????????????????.");
        return "redirect:/meeting/"+URLEncoder.encode(url, StandardCharsets.UTF_8)+"/setting/banner";
    }

    @PostMapping("/banner/enable")
    public String enableBannerImage(@AuthUser Account account, @PathVariable String url)
            throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);
        meetingService.enableBannerImage(meeting);
        return "redirect:/meeting/"+URLEncoder.encode(url, StandardCharsets.UTF_8)+"/setting/banner";
    }

    @PostMapping("banner/disable")
    public String disableBannerImage(@AuthUser Account account, @PathVariable String url)
            throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);
        meetingService.disableBannerImage(meeting);
        return "redirect:/meeting/"+URLEncoder.encode(url, StandardCharsets.UTF_8)+"/setting/banner";
    }

    @GetMapping("/meeting")
    public String viewMeeting(@AuthUser Account account, @PathVariable String url, Model model)
            throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);
        model.addAttribute(meeting);
        model.addAttribute(account);
        return "meeting/setting/meeting";
    }

    @PostMapping("/meeting/publish")
    public String publishMeeting(@AuthUser Account account, @PathVariable String url,
                                 RedirectAttributes attributes) throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);
        meetingService.publish(meeting);
        attributes.addFlashAttribute("message","????????? ??????????????????.");
        return "redirect:/meeting/"+URLEncoder.encode(url, StandardCharsets.UTF_8)+"/setting/meeting";
    }

    @PostMapping("/meeting/close")
    public String closeMeeting(@AuthUser Account account, @PathVariable String url,
                               RedirectAttributes attributes) throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);
        meetingService.close(meeting);
        attributes.addFlashAttribute("message","????????? ??????????????????.");
        return "redirect:/meeting/"+URLEncoder.encode(url, StandardCharsets.UTF_8)+"/setting/meeting";

    }

    @PostMapping("/meeting/remove")
    public String removeMeeting(@AuthUser Account account, @PathVariable String url,
                                Model model) throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);
        meetingService.remove(meeting);
        return "redirect:/";
    }

}
