package com.qwon.eat_together.config;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

// JdbcTokenRepositoryImpl는 persistence_logins 테이블이 필요
// 아무런 값 설정하지 않으면 Account로부터 생성
// token을 생성하여 jdbcTokenRepository에 전달
@Table(name="persistent_logins")
@Entity
@Getter @Setter
public class RememberLogin {

    @Id
    @Column(length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false, length = 64)
    private LocalDateTime lastUsed;

}
