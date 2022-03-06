package com.sammaru5.sammaru.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class StorageEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;

    private String filePath;
}
