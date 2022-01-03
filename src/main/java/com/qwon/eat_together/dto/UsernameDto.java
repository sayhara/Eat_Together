package com.qwon.eat_together.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UsernameDto {

    @NotBlank
    @Length(min=5, max=20)
    private String username;

}
