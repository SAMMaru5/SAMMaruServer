package com.sammaru5.sammaru.dto;

import com.sammaru5.sammaru.domain.ArticleEntity;
import lombok.Getter;

import java.sql.Timestamp;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
public class ArticleDTO {
    private String title;
    private String content;
    private Timestamp createDt;
    private String author;
    private Integer viewCnt;
    private Integer likeCnt;

    public ArticleDTO(ArticleEntity articleEntity) {
        copyProperties(articleEntity, this);
        this.createDt = articleEntity.getCreateTime();
        this.author = articleEntity.getUser().getUsername();
    }
}
