package com.qwon.eat_together.controller;

import com.qwon.eat_together.config.AuthUser;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Event;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.dto.EventFormDto;
import com.qwon.eat_together.repository.EventRepository;
import com.qwon.eat_together.service.EventService;
import com.qwon.eat_together.service.MeetingService;
import com.qwon.eat_together.validation.EventFormDtoValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;

@Controller
@RequestMapping("/meeting/{url}")
@RequiredArgsConstructor
public class EventController {

    private final MeetingService meetingService;
    private final EventService eventService;
    private final ModelMapper modelMapper;
    private final EventFormDtoValidator eventFormDtoValidator;
    private final EventRepository eventRepository;

    @InitBinder("eventFormDto")
    public void InitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(eventFormDtoValidator);
    }

    @GetMapping("/new-event")
    public String newMeetingCreate(@AuthUser Account account,
                                   @PathVariable String url, Model model) throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);

        model.addAttribute(account);
        model.addAttribute(meeting);
        model.addAttribute(new EventFormDto());

        return "event/form";

    }

    @PostMapping("/new-event")
    public String newMeetingSubmit(@AuthUser Account account, @PathVariable String url,
                                   EventFormDto eventFormDto, Errors errors, Model model)
            throws AccessDeniedException {

        Meeting meeting = meetingService.meetingUpdate(account, url);

        if(errors.hasErrors()){
            model.addAttribute(account);
            model.addAttribute(meeting);
            return "event/form";
        }

        Event event = eventService.createEvent(modelMapper.map(eventFormDto, Event.class), meeting, account);
        return "redirect:/meeting/"+URLEncoder.encode(meeting.getUrl(), StandardCharsets.UTF_8)+"/events/"+event.getId();
    }

    @GetMapping("/events/{id}")
    public String getEvent(@AuthUser Account account, @PathVariable String url,
                           @PathVariable Long id, Model model){

        model.addAttribute(account);
        model.addAttribute(eventRepository.findById(id).orElseThrow());
        model.addAttribute(meetingService.getMeeting(url));
        return "event/view";
    }
}
