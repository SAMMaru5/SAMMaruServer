package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.ArticleEntity;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private Timestamp createDt;
    private String author;
    private Integer viewCnt;
    private Integer likeCnt;
    private List<FileDTO> files = new ArrayList<>();

    public ArticleDTO(ArticleEntity articleEntity) {
        this.id = articleEntity.getId();
        this.title = articleEntity.getTitle();
        this.content = articleEntity.getContent();
        this.createDt = articleEntity.getCreateTime();
        this.author = articleEntity.getUser().getUsername();
        this.viewCnt = articleEntity.getViewCnt();
        this.likeCnt = articleEntity.getViewCnt();
        if(!articleEntity.getFiles().isEmpty()) this.files = articleEntity.getFiles().stream().map(FileDTO::new).collect(Collectors.toList());
    }
}
