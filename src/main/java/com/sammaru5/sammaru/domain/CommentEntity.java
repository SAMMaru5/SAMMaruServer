package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.web.request.CommentRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
public class CommentEntity {

    @Id @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    public CommentEntity (CommentRequest commentRequest, UserEntity user, ArticleEntity article) {
        this.content = commentRequest.getContent();
        this.article = article;
        this.user = user;
    }
}
