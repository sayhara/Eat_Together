package com.qwon.eat_together.service;

import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.dto.MeetingInfoDto;
import com.qwon.eat_together.dto.MeetingDto;
import com.qwon.eat_together.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

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

    public Meeting getMeeting(String url){
        Meeting meeting = meetingRepository.findByUrl(url);
        if(meeting==null){
            throw new IllegalArgumentException(url+"에 해당하는 모임이 없습니다.");
        }
        return meeting;
    }

    public Meeting meetingUpdate(Account account, String url) throws AccessDeniedException {

        Meeting meeting = getMeeting(url);
        if(!meeting.getManagers().contains(account)){ // 관리자가 아닌 경우
            throw new AccessDeniedException("해당 기능 사용 불가");
        }
        return meeting;
    }

    public void updateMeetingInfo(Meeting meeting, MeetingInfoDto meetingInfoDto) {
        modelMapper.map(meetingInfoDto,meeting);
    }

    public void updateMeetingImage(Meeting meeting, String image) {
        meeting.setImage(image);
    }

    public void enableBannerImage(Meeting meeting) {
        meeting.setUseBanner(true);
    }

    public void disableBannerImage(Meeting meeting) {
        meeting.setUseBanner(false);
    }

    public void publish(Meeting meeting) {
        meeting.publish();
    }

    public void close(Meeting meeting) {
        meeting.close();
    }

    public void remove(Meeting meeting) {
        if(!meeting.isPublished()){
            meetingRepository.delete(meeting);
        } else {
            throw new IllegalArgumentException("모임을 삭제할 수 없습니다.");
        }
    }
}
