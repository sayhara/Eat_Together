package com.qwon.eat_together.social;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable { // 직렬화 기능
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}