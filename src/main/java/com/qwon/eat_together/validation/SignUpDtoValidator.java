package com.qwon.eat_together.validation;

import com.qwon.eat_together.dto.SignUpDto;
import com.qwon.eat_together.repository.AccountRepository;
import com.qwon.eat_together.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpDtoValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpDto.class); // 검증할 클래스
    }

    @Override
    public void validate(Object target, Errors errors) {

        SignUpDto signUpDto=(SignUpDto)target;

        if(accountRepository.existsByUsername(signUpDto.getUsername())){
            errors.rejectValue("username","duplicate username",
                    "이미 사용중인 아이디입니다.");
        }

        if(accountRepository.existsByEmail(signUpDto.getEmail())) {
            errors.rejectValue("email", "duplicate email",
                    "이미 사용중인 이메일입니다.");
        }

        if(!signUpDto.getPassword().equals(signUpDto.getPasswordCheck())){
            errors.rejectValue("password","invalid password",
                    "패스워드가 일치하지 않습니다.");
        }
    }
}
