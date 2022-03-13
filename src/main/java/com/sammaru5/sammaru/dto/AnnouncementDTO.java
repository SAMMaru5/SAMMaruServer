package com.sammaru5.sammaru.dto;

import com.sammaru5.sammaru.domain.ArticleEntity;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class AnnouncementDTO {
    private String title;
    private String author;
    private Timestamp createDt;

    public AnnouncementDTO(ArticleEntity annoucement) {
        this.title = annoucement.getTitle();
        this.author = annoucement.getUser().getUsername();
        this.createDt = annoucement.getCreateTime();
    }
}
