package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.Article;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementDTO {
    private String title;
    private String author;
    private Timestamp createDt;

    public AnnouncementDTO(Article article) {
        copyProperties(article, this);
        this.author = article.getUser().getUsername();
        this.createDt = article.getCreateTime();
    }
}
