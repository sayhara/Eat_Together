package com.qwon.eat_together.service;

import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Event;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event createEvent(Event event, Meeting meeting, Account account){

        event.setCreatedBy(account);
        event.setMeeting(meeting);
        return eventRepository.save(event);
    }
}
