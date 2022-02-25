package com.qwon.eat_together.alarm;

import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Alarm;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.dto.AlarmType;
import com.qwon.eat_together.repository.AlarmRepository;
import com.qwon.eat_together.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
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

    private void createAlarm(Meeting meeting, Account account, String message, AlarmType alarmType) {
        Alarm alarm=new Alarm();
        alarm.setTitle(meeting.getTitle());
        alarm.setLink("/meeting/"+URLEncoder.encode(meeting.getUrl(), StandardCharsets.UTF_8));
        alarm.setChecked(false);
        alarm.setCreatedDateTime(LocalDateTime.now());
        alarm.setMessage(message);
        alarm.setAccount(account);
        alarm.setAlarmType(alarmType);
        alarmRepository.save(alarm);
    }

}
