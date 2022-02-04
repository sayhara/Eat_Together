package com.qwon.eat_together.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor

public class Meeting {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Set<Account> manager=new HashSet<>();

    @ManyToMany
    private Set<Account> members=new HashSet<>();

    @Column(unique = true)
    private String url;

    private String title;

    private String short_message;

    private String long_message;

    private String image;

    private LocalDateTime publishTime;

    private LocalDateTime closedTime;

    private LocalDateTime recruitTime;

    private boolean recruiting;

    private boolean published;

    private boolean closed;

    private boolean banner;

}
