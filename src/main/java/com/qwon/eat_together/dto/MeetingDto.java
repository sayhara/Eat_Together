package com.qwon.eat_together.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class MeetingDto {

    @NotBlank
    @Length(min=5, max=50)
    private String url;

    @NotBlank
    private String title;

    private String short_message;

    private String long_message;
}
