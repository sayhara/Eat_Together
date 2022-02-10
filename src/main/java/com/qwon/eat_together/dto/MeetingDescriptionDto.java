package com.qwon.eat_together.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MeetingDescriptionDto {

    private String short_message;

    private String long_message;
}
