package com.qwon.eat_together.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Meeting {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="account_id")
    private Account manager;

    @ManyToMany
    private List<Account> members=new ArrayList<>();

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

    public void setManager(Account manager) {
        this.manager=manager;
        manager.setMeeting(this);
    }
}
