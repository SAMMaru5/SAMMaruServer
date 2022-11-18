package com.sammaru5.sammaru.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public static ArticleLike createArticleLike(User user, Article article) {
        return ArticleLike.builder()
                .user(user)
                .article(article)
                .build();
    }

    @Builder
    private ArticleLike(User user, Article article){
        this.user = user;
        belongToArticle(article);
    }

    private void belongToArticle(Article article){
        this.article = article;
        article.addArticleLike(this);
    }
}
