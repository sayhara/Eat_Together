package com.qwon.eat_together.dto;

import com.qwon.eat_together.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;

@Data
@NoArgsConstructor
public class Profile {

    @Length(max=30)
    private String bio;

    private int age;

    private String job;

    private String location;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    public Profile(Account account) {
        this.bio = account.getBio();
        this.age = account.getAge();
        this.job = account.getJob();
        this.location = account.getLocation();
        this.profileImage=account.getProfileImage();
    }
}
