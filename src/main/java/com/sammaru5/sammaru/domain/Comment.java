package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.web.request.CommentRequest;
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

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Comment(CommentRequest commentRequest, User user, Article article) {
        this.content = commentRequest.getContent();
        this.article = article;
        this.user = user;
    }
}
