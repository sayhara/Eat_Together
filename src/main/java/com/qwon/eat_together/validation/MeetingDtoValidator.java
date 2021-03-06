package com.qwon.eat_together.validation;

import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.dto.MeetingDto;
import com.qwon.eat_together.dto.SignUpDto;
import com.qwon.eat_together.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MeetingDtoValidator implements Validator {

    private final MeetingRepository meetingRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(MeetingDto.class); // 검증할 클래스
    }

    @Override
    public void validate(Object target, Errors errors) {

        MeetingDto meetingDto=(MeetingDto) target;

        if(meetingRepository.existsByUrl(meetingDto.getUrl())){
            errors.rejectValue("url","duplicate url",
                    "이미 사용중인 경로입니다.");
        }
    }
}
