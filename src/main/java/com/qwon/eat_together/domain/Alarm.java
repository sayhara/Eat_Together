package com.qwon.eat_together.domain;

import com.qwon.eat_together.dto.AlarmType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode
public class Alarm {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String link;

    private String message;

    private boolean checked;

    @ManyToOne
    private Account account;

    private LocalDateTime createdTime;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;
}
