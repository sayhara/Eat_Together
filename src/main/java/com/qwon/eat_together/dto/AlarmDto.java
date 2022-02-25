package com.qwon.eat_together.dto;

import com.qwon.eat_together.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // NullPointerException 방지
public class AlarmDto {

    private boolean meetingCreated; // 모임 개설

    private boolean meetingUpdated; // 관심있는 곳

    public AlarmDto(Account account){
        this.meetingCreated=account.isMeetingCreated();
        this.meetingUpdated=account.isMeetingUpdated();
    }
    
}
