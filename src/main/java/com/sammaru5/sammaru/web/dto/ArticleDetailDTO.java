package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.Article;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleDetailDTO {
    private Long id;
    private String title;
    private String content;
    private Timestamp createDt;
    private String author;
    private Integer viewCnt;
    private Integer likeCnt;
    private Boolean isLiked;
    private List<FileDTO> files = new ArrayList<>();
    private Long prevArticleId;
    private Long nextArticleId;

    public static ArticleDetailDTO from(Article article, Long prevArticleId, Long nextArticleId) {return new ArticleDetailDTO(article, prevArticleId, nextArticleId);}

    private ArticleDetailDTO(Article article, Long prevArticleId, Long nextArticleId) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createDt = article.getCreateTime();
        this.author = article.getUser().getUsername();
        this.viewCnt = article.getViewCnt();
        this.likeCnt = article.getLikeCnt();
        this.isLiked = article.getIsLiked();
        if (article.getFiles() != null && !article.getFiles().isEmpty()) {
            this.files = article.getFiles().stream().map(FileDTO::new).collect(Collectors.toList());
        }
        if (prevArticleExist(prevArticleId)){
            this.prevArticleId = prevArticleId;
        }
        if (nextArticleExist(nextArticleId)){
            this.nextArticleId = nextArticleId;
        }
    }

    private boolean prevArticleExist(Long prevArticleId){
        if(this.id != prevArticleId)
            return true;
        this.prevArticleId = 0L;
        return false;
    }

    private boolean nextArticleExist(Long nextArticleId){
        if(this.id != nextArticleId)
            return true;
        this.nextArticleId = 0L;
        return false;
    }
}
