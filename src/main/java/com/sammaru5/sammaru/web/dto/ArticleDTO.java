package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.File;
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

    public static ArticleDTO toDto(Article article) {
        return new ArticleDTO(article);
    }

    public static ArticleDTO toDtoWithFile(Article article, List<File> files) {
        return new ArticleDTO(article, files);
    }

    private ArticleDTO(Article article, List<File> files) {
        this(article);
        this.files = files.stream().map(FileDTO::new).collect(Collectors.toList());
    }
    private ArticleDTO(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createDt = article.getCreateTime();
        this.author = article.getUser().getUsername();
        this.viewCnt = article.getViewCnt();
        this.likeCnt = article.getViewCnt();
    }
}
