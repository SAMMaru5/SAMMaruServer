package com.sammaru5.sammaru.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
