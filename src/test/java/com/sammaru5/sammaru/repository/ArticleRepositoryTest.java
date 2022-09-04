package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.Board;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.domain.UserAuthority;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("@CreatedDate 동작 확인")
    public void checkArticleCreateTime() {
        // given
        User user = User.builder()
                .studentId("1111111")
                .username("test")
                .password("1234")
                .email("test@gmail.com")
                .point(0L)
                .role(UserAuthority.ROLE_TEMP)
                .build();
        userRepository.save(user);

        Board board = new Board(1L, "boardName", "description");
        boardRepository.save(board);

        // when
        Article article = new Article(null, "title", "content", 0, 0, board, user, null);
        articleRepository.save(article);

        // then
        List<Article> articleList = articleRepository.findAll();
        Article generatedArticle = articleList.get(0);

        System.out.println("generatedArticle.getCreateTime() = " + generatedArticle.getCreateTime());
        assertThat(generatedArticle.getCreateTime()).isBefore(Instant.now());
    }
}