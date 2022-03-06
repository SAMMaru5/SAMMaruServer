package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.request.ArticleRequest;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Entity
public class ArticleEntity {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;

    @CreationTimestamp
    private Timestamp createTime;

    public ArticleEntity(ArticleRequest articleRequest, BoardEntity board, UserEntity user) {
        this(articleRequest.getTitle(), articleRequest.getContent());
        this.board = board;
        this.user = user;
    }

    public ArticleEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

}
