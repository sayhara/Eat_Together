package com.qwon.eat_together.service;

import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.dto.MeetingDto;
import com.qwon.eat_together.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final ModelMapper modelMapper;

    public Meeting createMeeting(MeetingDto meetingDto, Meeting meeting, Account account){
        modelMapper.map(meetingDto,meeting);
        Meeting newMeeting = meetingRepository.save(meeting);
        newMeeting.setManager(account);
        return newMeeting;
    }
}
