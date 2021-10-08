package com.qwon.eat_together.domain;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;

@Data
public class Profile {

    private String bio;

    private String age;

    private String job;

    private String location;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    public Profile(Account account) {
        this.bio = account.getBio();
        this.age = account.getAge();
        this.job = account.getJob();
        this.location = account.getLocation();
        this.profileImage = account.getProfileImage();
    }
}
