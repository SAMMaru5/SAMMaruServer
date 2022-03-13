package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.request.ArticleRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "article")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEntity {

    public ArticleEntity(ArticleRequest articleRequest, BoardEntity board, UserEntity user) {
        this(articleRequest.getTitle(), articleRequest.getContent());
        this.board = board;
        this.user = user;
    }

    public ArticleEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

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

}