package com.qwon.eat_together.validation;

import com.qwon.eat_together.dto.EventFormDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EventFormDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return EventFormDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        EventFormDto eventFormDto=(EventFormDto) target;

        if(eventFormDto.getEndTime().isBefore(eventFormDto.getStartTime())){
            errors.rejectValue("endTime","wrong.dateTime",
                    "모임 종료일시를 다시 확인해주세요.");
        }

    }
}
