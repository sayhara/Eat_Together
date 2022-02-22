package com.qwon.eat_together.config;

import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Alarm;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.dto.AlarmType;
import com.qwon.eat_together.dto.MeetingCreatedAlarm;
import com.qwon.eat_together.dto.MeetingUpdateAlarm;
import com.qwon.eat_together.repository.AccountRepository;
import com.qwon.eat_together.repository.AlarmRepository;
import com.qwon.eat_together.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Async
@Component
@RequiredArgsConstructor
@Transactional
public class MeetingListener {

    private final MeetingRepository meetingRepository;
    private final AlarmRepository alarmRepository;

    @EventListener
    public void handleMeetingCreatedEvent(MeetingCreatedAlarm meetingCreatedAlarm){
        Meeting meeting=meetingRepository.findMeetingWithManagersAndMembersById(meetingCreatedAlarm.getMeeting().getId());
        Set<Account> accounts=new HashSet<>();
        accounts.addAll(meeting.getManagers());
        accounts.addAll(meeting.getMembers());

        accounts.forEach(account -> {
            if(account.isMeetingCreated()){
                createAlarm(meeting, account, AlarmType.MEETING_CREATED);
            }
        });
    }

    @EventListener
    public void handleMeetingUpdatedEvent(MeetingUpdateAlarm meetingUpdateAlarm){

        Meeting meeting=meetingRepository.
                findMeetingWithManagersAndMembersById(meetingUpdateAlarm.getMeeting().getId());

        Set<Account> accounts=new HashSet<>();
        accounts.addAll(meeting.getManagers());
        accounts.addAll(meeting.getMembers());

        accounts.forEach(account -> {
            if(account.isMeetingUpdated()){
                createAlarm(meeting, account, AlarmType.MEETING_UPDATED);

            }
        });
    }


    public void createAlarm(Meeting meeting, Account account, AlarmType alarmType) {
        Alarm alarm=new Alarm();
        alarm.setTitle(meeting.getTitle());
        alarm.setLink("/meeting/"+ URLEncoder.encode(meeting.getUrl(), StandardCharsets.UTF_8));
        alarm.setChecked(false);
        alarm.setCreatedTime(LocalDateTime.now());
        alarm.setMessage(meeting.getShort_message());
        alarm.setAccount(account);
        alarm.setAlarmType(alarmType);
        alarmRepository.save(alarm);
    }

}
