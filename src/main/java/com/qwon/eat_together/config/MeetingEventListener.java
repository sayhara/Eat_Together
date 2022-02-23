package com.qwon.eat_together.config;

import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Alarm;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.dto.AlarmType;
import com.qwon.eat_together.dto.MeetingCreatedEvent;
import com.qwon.eat_together.dto.MeetingUpdatedEvent;
import com.qwon.eat_together.repository.AlarmRepository;
import com.qwon.eat_together.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Async
@Component
@RequiredArgsConstructor
@Transactional
public class MeetingEventListener {

    private final MeetingRepository meetingRepository;
    private final AlarmRepository alarmRepository;

    @EventListener
    public void handleMeetingCreatedEvent(MeetingCreatedEvent meetingCreatedEvent){
        Meeting meeting=meetingRepository.findMeetingWithManagersAndMembersById(meetingCreatedEvent.getMeeting().getId());
        Set<Account> accounts=new HashSet<>();
        accounts.addAll(meeting.getManagers());
        accounts.addAll(meeting.getMembers());

        accounts.forEach(account -> {
            if(account.isMeetingCreated()){
                createAlarm(meeting, account, meeting.getShort_message(), AlarmType.MEETING_CREATED);
            }
        });
    }

    @EventListener
    public void handleMeetingUpdatedEvent(MeetingUpdatedEvent meetingUpdatedEvent){

        Meeting meeting=meetingRepository.
                findMeetingWithManagersAndMembersById(meetingUpdatedEvent.getMeeting().getId());

        Set<Account> accounts=new HashSet<>();
        accounts.addAll(meeting.getManagers());
        accounts.addAll(meeting.getMembers());

        accounts.forEach(account -> {
            if(account.isMeetingUpdated()){
                createAlarm(meeting, account, meetingUpdatedEvent.getMessage(), AlarmType.MEETING_UPDATED);

            }
        });
    }

    public void createAlarm(Meeting meeting, Account account, String message, AlarmType alarmType) {
        Alarm alarm=new Alarm();
        alarm.setTitle(meeting.getTitle());
        alarm.setLink("/meeting/"+meeting.getUrl());
        alarm.setChecked(false);
        alarm.setCreatedTime(LocalDateTime.now());
        alarm.setMessage(message);
        alarm.setAccount(account);
        alarm.setAlarmType(alarmType);
        alarmRepository.save(alarm);
    }

}
