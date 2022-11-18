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

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
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
        this.user = user;

        belongToArticle(article);
    }

    public void modifyContent(CommentRequest commentRequest) {
        this.content = commentRequest.getContent();
    }

    //==연관관계 메서드==//
    private void belongToArticle(Article article){
        this.article = article;
        article.addComment(this);
    }

}
