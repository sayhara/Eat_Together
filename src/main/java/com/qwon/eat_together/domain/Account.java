package com.qwon.eat_together.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    // H2 : SEQUENCE, MySQL : IDENTITY
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    private String passwordCheck;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private String bio;

    private int age;

    private String job;

    private String location;

    private boolean created;

    private boolean result;

    @Column(name="update_thing")
    private boolean update;

    @OneToOne(mappedBy = "manager")
    private Meeting meeting;

    public String getProfileImage(){
        return profileImage!= null ? profileImage : "/images/profileImage.jpg";
    }

}
