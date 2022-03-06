package com.sammaru5.sammaru.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class CommentEntity {

    @Id @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
