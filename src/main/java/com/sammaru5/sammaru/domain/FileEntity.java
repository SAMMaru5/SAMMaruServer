package com.sammaru5.sammaru.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "file")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;
    private String filePath;
    private String fileName;

    //== 연관관계 메서드 ==//
    public void belongToArticle(ArticleEntity article) {
        this.article = article;
    }
}
