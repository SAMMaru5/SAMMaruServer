package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleLikeRepositoryTest {

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("좋아요 추가")
    public void addArticleLike() {
        //given
        User user = getUser();
        ArticleLike articleLike = getArticleLike(user, getArticle(user));

        //when
        ArticleLike savedArticleLike = articleLikeRepository.save(articleLike);

        //then
        assertThat(savedArticleLike).isEqualTo(articleLike);
    }

    @Test
    @DisplayName("좋아요 삭제")
    public void deleteArticleLike() {
        //given
        User user = getUser();
        ArticleLike articleLike = getArticleLike(user, getArticle(user));
        ArticleLike savedArticleLike = articleLikeRepository.save(articleLike);
        assertThat(articleLikeRepository.existsById(savedArticleLike.getId())).isTrue();

        //when
        articleLikeRepository.delete(savedArticleLike);

        //then
        assertThat(articleLikeRepository.existsById(savedArticleLike.getId())).isFalse();
    }

    @Test
    @DisplayName("특정 회원의 좋아요 모두 삭제")
    public void deleteAllArticleLikeByUser() {
        //given
        User user = getUser();
        User user2 = getUser();
        Article article1 = getArticle(user);
        Article article2 = getArticle(user);
        Article article3 = getArticle(user);
        Article article4 = getArticle(user2);
        Article article5 = getArticle(user2);

        ArticleLike articleLike1 = articleLikeRepository.save(getArticleLike(user, article1));
        ArticleLike articleLike2 = articleLikeRepository.save(getArticleLike(user2, article2));
        ArticleLike articleLike3 = articleLikeRepository.save(getArticleLike(user, article4));
        ArticleLike articleLike4 = articleLikeRepository.save(getArticleLike(user2, article4));

        //when
        articleLikeRepository.deleteAllByUserId(user2.getId());

        //then
        List<ArticleLike> allArticleLike = articleLikeRepository.findAll();
        assertThat(allArticleLike).hasSameElementsAs(Arrays.asList(articleLike1, articleLike3));
    }

    @Test
    @DisplayName("특정 게시글의 좋아요 모두 삭제")
    public void deleteAllArticleLikeByArticle() {
        //given
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            users.add(getUser());
        }
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            articles.add(getArticle(users.get(0)));
        }

        for (int i = 0; i < 2; i++) {
            articleLikeRepository.save(getArticleLike(users.get(i), articles.get(0)));
        }
        List<ArticleLike> articleLikesOfArticle2 = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            articleLikesOfArticle2.add(articleLikeRepository.save(getArticleLike(users.get(i), articles.get(1))));
        }

        //when
        articleLikeRepository.deleteAllByArticleId(articles.get(0).getId());

        //then
        List<ArticleLike> allArticleLike = articleLikeRepository.findAll();
        assertThat(allArticleLike).hasSameElementsAs(articleLikesOfArticle2);
    }

    User getUser() {
        return userRepository.save(
                new User(null, "studentId", "username", "password", "email", 0L, 10, 3, UserAuthority.ROLE_MEMBER));
    }

    Article getArticle(User user) {
        return articleRepository.save(
                new Article(null, "title", "content", 0, 0, null, getBoard(), user, null));
    }

    ArticleLike getArticleLike(User user, Article article) {
        return ArticleLike.builder()
                .user(user)
                .article(article)
                .build();
    }

    Board getBoard() {
        return boardRepository.save(
                new Board(null, "board", ""));
    }
}


