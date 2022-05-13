package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.request.ArticleRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEntity {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;

    @CreationTimestamp
    private Timestamp createTime;

    private Integer viewCnt;
    private Integer likeCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @OneToMany(mappedBy = "article")
    private List<FileEntity> files = new ArrayList<>();

    public void plusViewCnt() {
        this.viewCnt++;
    }

    public void minusViewCnt() {
        this.viewCnt--;
    }
    public void plusLikeCnt() {
        this.likeCnt++;
    }
    public void minusLikeCnt() {
        this.likeCnt--;
    }

    public void modifyArticle(ArticleRequest articleRequest) {
        this.title = articleRequest.getTitle();
        this.content = articleRequest.getContent();
    }

    public ArticleEntity(ArticleRequest articleRequest, BoardEntity board, UserEntity user) {
        this(articleRequest.getTitle(), articleRequest.getContent());
        this.board = board;
        this.user = user;
        this.viewCnt = 0;
        this.likeCnt = 0;
    }

    public ArticleEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
