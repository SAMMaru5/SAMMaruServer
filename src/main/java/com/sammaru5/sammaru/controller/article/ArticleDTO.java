package com.sammaru5.sammaru.controller.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ArticleDTO {
    private String title;
    private String content;
    private Timestamp createDt;
    private String author;
    private Integer viewCnt;
    private Integer likeCnt;

    public ArticleDTO(ArticleEntity articleEntity) {
        this.title = articleEntity.getTitle();
        this.content = articleEntity.getContent();
        this.createDt = articleEntity.getCreateTime();
        this.author = articleEntity.getUser().getUsername();
        this.viewCnt = articleEntity.getViewCnt();
        this.likeCnt = articleEntity.getLikeCnt();
    }
}
