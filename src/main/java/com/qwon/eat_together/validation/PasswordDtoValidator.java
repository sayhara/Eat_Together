package com.qwon.eat_together.validation;

import com.qwon.eat_together.dto.PasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PasswordDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(PasswordDto.class); // 검증할 클래스
    }

    @Override
    public void validate(Object target, Errors errors) {

        PasswordDto passwordDto=(PasswordDto) target;

        if(!passwordDto.getPassword().equals(passwordDto.getPasswordCheck())){
            errors.rejectValue("password","invalid password",
                    "새 패스워드가 일치하지 않습니다.");
        }

    }
}
