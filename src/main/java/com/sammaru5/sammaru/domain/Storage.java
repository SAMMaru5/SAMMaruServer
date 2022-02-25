package com.sammaru5.sammaru.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class Storage {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    private String filePath;
}
