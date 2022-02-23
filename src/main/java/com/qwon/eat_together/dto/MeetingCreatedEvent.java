package com.qwon.eat_together.dto;

import com.qwon.eat_together.domain.Meeting;
import lombok.Getter;

@Getter
public class MeetingCreatedEvent {

    private Meeting meeting;

    public MeetingCreatedEvent(Meeting meeting){
        this.meeting=meeting;
    }

}
