package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.web.request.CommentRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Comment createComment(CommentRequest commentRequest, Article article, User user) {
        return Comment.builder()
                .commentRequest(commentRequest)
                .article(article)
                .user(user)
                .build();
    }

    @Builder
    private Comment(CommentRequest commentRequest, User user, Article article) {
        this.content = commentRequest.getContent();
        this.article = article;
        this.user = user;
    }
}
