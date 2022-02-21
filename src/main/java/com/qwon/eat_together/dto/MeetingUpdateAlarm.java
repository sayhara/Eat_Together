package com.qwon.eat_together.dto;

import com.qwon.eat_together.domain.Meeting;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MeetingUpdateAlarm {

    private final Meeting meeting;

    private final String message;
}
