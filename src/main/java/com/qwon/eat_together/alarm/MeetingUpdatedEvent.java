package com.qwon.eat_together.alarm;

import com.qwon.eat_together.domain.Meeting;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MeetingUpdatedEvent {

    private final Meeting meeting;

    private final String message;
}
