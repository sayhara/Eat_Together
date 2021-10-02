package com.qwon.eat_together.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignUpDto {

    @NotBlank
    @Length(min=5, max=20)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min=8, max=20)
    private String password;

    @NotBlank
    @Length(min=8, max=20)
    private String passwordCheck;
}
