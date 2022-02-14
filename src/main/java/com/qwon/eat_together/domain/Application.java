package com.qwon.eat_together.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
public class Application {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Event event; // 여러 개의 신청서 & 하나의 모임

    @ManyToOne
    private Account account; // 신청한 사람

    private boolean accepted;

    private boolean attended;

}
