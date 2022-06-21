package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.ArticleEntity;
import lombok.Getter;

import java.sql.Timestamp;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
public class AnnouncementDTO {
    private String title;
    private String author;
    private Timestamp createDt;

    public AnnouncementDTO(ArticleEntity articleEntity) {
        copyProperties(articleEntity, this);
        this.author = articleEntity.getUser().getUsername();
        this.createDt = articleEntity.getCreateTime();
    }
}
