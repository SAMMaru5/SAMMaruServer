package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.*;
import com.sammaru5.sammaru.repository.*;
import com.sammaru5.sammaru.web.request.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRemoveServiceTest {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final CommentRepository commentRepository;

    private final ArticleRemoveService articleRemoveService;

    @Mock
    CommentRequest commentRequest = new CommentRequest();

    @Test
    @DisplayName("게시글 삭제시 관련 좋아요들도 삭제되는지 확인")
    void removeArticleWithLike() {
        //given
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            users.add(userRepository.save(new User(null, "studentId" + i, "username", "password", "email", 0L, 0, 0, UserAuthority.ROLE_MEMBER)));
        }
        Board board = boardRepository.save(new Board(null, "name", "description"));
        Article article1 = articleRepository.save(new Article(null, "title1", "content", 0, 0, null, board, users.get(0), null));
        Article article2 = articleRepository.save(new Article(null, "title2", "content", 0, 0, null, board, users.get(0), null));

        articleLikeRepository.save(new ArticleLike(null, users.get(0), article1));
        articleLikeRepository.save(new ArticleLike(null, users.get(1), article1));
        ArticleLike articleLike = articleLikeRepository.save(new ArticleLike(null, users.get(0), article2));

        //when
        articleRemoveService.removeArticle(article1.getId(), article1.getUser().getId(), 0L);

        //then
        Assertions.assertThat(articleLikeRepository.findAll()).hasSameElementsAs(List.of(articleLike));
    }

    @Test
    @DisplayName("댓글이 존재하는 게시글이 정상적으로 삭제되는지 확인")
    void removeArticleWithComment() {
        //given
        User user = userRepository.save(new User(null, "studentId", "username", "password", "email", 0L, 0, 0, UserAuthority.ROLE_MEMBER));
        Board board = boardRepository.save(new Board(null, "name", "description"));
        Article article = articleRepository.save(new Article(null, "title1", "content", 0, 0, null, board, user, null));

        Mockito.when(commentRequest.getContent()).thenReturn("comment");
        Comment comment = commentRepository.save(Comment.createComment(commentRequest, article, user));

        //when
        articleRemoveService.removeArticle(article.getId(), article.getUser().getId(), board.getId());

        //then
        Assertions.assertThat(articleRepository.findById(article.getId())).isEmpty();
        Assertions.assertThat(commentRepository.findByArticle(article).size()).isEqualTo(0);
    }
}