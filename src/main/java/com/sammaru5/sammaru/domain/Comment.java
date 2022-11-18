package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.web.request.CommentRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
        this.article = article;
        this.user = user;
    }

    public void modifyContent(CommentRequest commentRequest) {
        this.content = commentRequest.getContent();
    }

    /**
     * 해당 Comment의 작성자인지 판별하는 메서드
     * @param user
     * @return
     */
    public boolean isWrittenBy(User user) {
        return this.getUser().equals(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (id == null) return false;
        if (!(o instanceof Comment))
            return false;

        final Comment comment = (Comment)o;

        return id.equals(comment.getId());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
