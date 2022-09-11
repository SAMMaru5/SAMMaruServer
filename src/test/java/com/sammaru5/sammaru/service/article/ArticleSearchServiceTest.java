package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.WithMockCustomUser;
import com.sammaru5.sammaru.config.security.SecurityUtil;
import com.sammaru5.sammaru.domain.*;
import com.sammaru5.sammaru.repository.ArticleLikeRepository;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleSearchServiceTest {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleSearchService articleSearchService;

    List<User> users;
    List<Article> articles;
    Board board;

    @BeforeEach()
    void setUp() {
        users = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            users.add(userRepository.save(new User(null, "studentId" + i, "username", "password", "email", 0L, 0, 0, UserAuthority.ROLE_MEMBER)));
        }

        board = boardRepository.save(new Board(null, "name", "description"));

        articles = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            articles.add(articleRepository.save(new Article(null, "title" + i, "content", 0, 0, null, board, users.get(0), null)));
        }
    }

    @Test
    @DisplayName("게시글 정보를 가져올때 좋아요 개수가 잘 반환되는지 확인")
    void findArticleCheckLikeCnt() {
        //given
        for (User user : users) {
            articleLikeRepository.save(new ArticleLike(null, user, articles.get(0)));
        }
        articleLikeRepository.save(new ArticleLike(null, users.get(0), articles.get(1)));

        // when
        ArticleDTO article1 = articleSearchService.findArticle(articles.get(0).getId());
        ArticleDTO article2 = articleSearchService.findArticle(articles.get(1).getId());

        // then
        assertThat(article1.getLikeCnt()).isEqualTo(users.size());
        Assertions.assertThat(article2.getLikeCnt()).isEqualTo(1);
    }

    @Test
    @DisplayName("로그인하지 않고 게시글 정보를 가져올때 좋아요 여부가 잘 반환되는지 확인")
    void findArticleCheckIsLikedWhenNotLogin() {
        ArticleDTO articleFindByNotUser = articleSearchService.findArticle(articles.get(0).getId());
        Assertions.assertThat(articleFindByNotUser.getIsLiked()).isNull();
    }

    @Test
    @DisplayName("로그인 후 게시글 정보를 가져올때 좋아요 여부가 잘 반환되는지 확인")
    @Sql(statements = "ALTER TABLE user AUTO_INCREMENT = 1", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @WithMockCustomUser(userId = "1")
    void findArticleCheckIsLikedWhenLogin() {
        // when
        Assertions.assertThat(SecurityUtil.getCurrentUserId()).isEqualTo(users.get(0).getId());
        articleLikeRepository.save(new ArticleLike(null, users.get(0), articles.get(0)));

        // then
        ArticleDTO articleFindByUser1 = articleSearchService.findArticle(articles.get(0).getId());
        Assertions.assertThat(articleFindByUser1.getIsLiked()).isTrue();
    }
}