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

    public boolean isIndelible(User user) {
        // TODO 간단하게 이렇게 짜보았는데 작성자와 관리자를 분리해서 메서드가 있으면 더 좋을 것 같아요.
        // 좋은 의견 있으면 추가해주세요.
        return !this.getUser().equals(user) && user.getRole() != UserAuthority.ROLE_ADMIN;
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
