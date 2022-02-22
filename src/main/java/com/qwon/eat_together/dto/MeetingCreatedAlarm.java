package com.qwon.eat_together.dto;

import com.qwon.eat_together.domain.Meeting;
import lombok.Getter;

@Getter
public class MeetingCreatedAlarm {

    private Meeting meeting;

    public MeetingCreatedAlarm(Meeting meeting){
        this.meeting=meeting;
    }

}
