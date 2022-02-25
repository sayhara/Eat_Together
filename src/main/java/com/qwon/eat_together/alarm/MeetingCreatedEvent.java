package com.qwon.eat_together.alarm;

import com.qwon.eat_together.domain.Meeting;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

@Getter
@RequiredArgsConstructor
public class MeetingCreatedEvent {

    private final Meeting meeting;

    private final String message;

}
