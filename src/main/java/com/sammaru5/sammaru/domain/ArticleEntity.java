package com.sammaru5.sammaru.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class ArticleEntity {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

}
