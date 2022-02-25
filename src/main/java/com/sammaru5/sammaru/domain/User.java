package com.sammaru5.sammaru.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity @Getter
public class User {

    @Id @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private Long studentId;
    private String email;
    private String role;
    private Long point;
}
