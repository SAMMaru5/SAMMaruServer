package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.*;
import com.sammaru5.sammaru.repository.ArticleLikeRepository;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("좋아요 개수 포함, 게시글 정보가 제대로 가져와지는지 확인")
    void findArticle() {
        //given
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            users.add(userRepository.save(new User(null, "studentId" + i, "username", "password", "email", 0L, 0, 0, UserAuthority.ROLE_MEMBER)));
        }

        Board board = boardRepository.save(new Board(null, "name", "description"));

        List<Article> articles = new ArrayList<>();
        for(int i=0; i<2; i++) {
            articles.add(articleRepository.save(new Article(null, "title" + i, "content", 0, 0, board, users.get(0), null)));
        }

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
}