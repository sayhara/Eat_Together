package com.qwon.eat_together.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordDto {

    @Length(min=8, max=20)
    private String password;

    @Length(min=8, max=20)
    private String passwordCheck;
}
