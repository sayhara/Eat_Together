package com.qwon.eat_together.domain;

import com.qwon.eat_together.config.UserAccount;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Meeting {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    private Set<Account> managers=new HashSet<>();

    @ManyToMany
    private Set<Account> members=new HashSet<>();

    @Column(unique = true)
    private String url;

    private String title;

    private String short_message;

    private String long_message;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String image;

    private LocalDateTime publishTime;

    private LocalDateTime closedTime;

    private LocalDateTime recruitTime;

    private boolean recruiting;

    private boolean published;

    private boolean closed;

    private boolean useBanner;

    public void setManager(Account account) {
        this.managers.add(account);
    }

    public String getImage() {
        return image != null ? image : "/images/default_banner.jpg";
    }

    public boolean isJoinable(UserAccount userAccount){
        Account account = userAccount.getAccount();
        return recruiting && this.published
                && !members.contains(account) && !managers.contains(account);
    }

    public boolean isMember(UserAccount userAccount){
        return members.contains(userAccount.getAccount());
    }

    public boolean isManager(UserAccount userAccount){
        return managers.contains(userAccount.getAccount());
    }

    public void publish(){

        if(!this.closed && !this.published){
            this.published=true;
            this.publishTime=LocalDateTime.now();
        } else {
            throw new RuntimeException("모임을 공개할 수 없는 상태입니다.");
        }
    }

    public void close(){

        if(!this.closed && this.published){
            this.closed=true;
            this.closedTime=LocalDateTime.now();
        } else {
            throw new RuntimeException("모임을 종료할 수 없는 상태입니다.");
        }
    }
}
