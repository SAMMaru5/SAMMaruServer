package com.sammaru5.sammaru.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Article {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
