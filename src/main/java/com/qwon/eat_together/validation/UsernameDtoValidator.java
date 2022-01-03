package com.qwon.eat_together.validation;

import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.dto.UsernameDto;
import com.qwon.eat_together.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UsernameDtoValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UsernameDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UsernameDto usernameDto= (UsernameDto) target;
        if(accountRepository.existsByUsername(usernameDto.getUsername())){
            errors.rejectValue("username","invalid username",
                    "이미 사용중인 닉네임입니다.");
        }
    }
}
