package com.qwon.eat_together.dto;

import com.qwon.eat_together.domain.Meeting;
import lombok.Getter;

@Getter
public class MeetingCreateAlarm {

    private Meeting meeting;

    public MeetingCreateAlarm(Meeting meeting){
        this.meeting=meeting;
    }

}
