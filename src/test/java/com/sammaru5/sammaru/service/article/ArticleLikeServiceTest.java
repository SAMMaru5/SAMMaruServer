package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.ArticleLike;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ArticleLikeRepository;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleLikeServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleLikeRepository articleLikeRepository;

    @InjectMocks
    private ArticleLikeService articleLikeService;

    User getUser(Long userId) {
        return new User(userId, "studentId", "username", "password", "email", 0L, 10, 3, UserAuthority.ROLE_MEMBER);
    }

    Article getArticle(Long articleId) {
        return new Article(articleId, "title", "content", 0, 0, null, null, null);
    }

    @Test
    @DisplayName("게시글에 좋아요 누르기")
    void giveArticleLike() {
        //given
        User user = getUser(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Article article = getArticle(1L);
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));

        ArticleLike articleLike = new ArticleLike(1L, user, article);
        try (MockedStatic<ArticleLike> mockedStatic = mockStatic(ArticleLike.class)) {
            mockedStatic.when(() -> ArticleLike.createArticleLike(any(), any())).thenReturn(articleLike);
            when(articleLikeRepository.save(articleLike)).thenReturn(articleLike);
            when(articleLikeRepository.findById(articleLike.getId())).thenReturn(Optional.of(articleLike));
            //when
            Long articleLikeId = articleLikeService.giveArticleLike(article.getId(), user.getId());
            //then
            assertThat(articleLikeRepository.findById(articleLikeId).get()).isEqualTo(articleLike);
        }
    }

    @Test
    @DisplayName("이미 좋아요를 누른 게시글에 좋아요를 누르는 경우")
    void checkArticleLikeDuplicate() {
        //given
        User user = getUser(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Article article = getArticle(1L);
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));

        ArticleLike articleLike = new ArticleLike(1L, user, article);
        try (MockedStatic<ArticleLike> mockedStatic = mockStatic(ArticleLike.class)) {
            mockedStatic.when(() -> ArticleLike.createArticleLike(any(), any())).thenReturn(articleLike);
            when(articleLikeRepository.existsByArticleIdAndUserId(user.getId(), article.getId())).thenReturn(true);
            //when
            CustomException exception = assertThrows(
                    CustomException.class,
                    () -> articleLikeService.giveArticleLike(article.getId(), user.getId()));
            //then
            System.out.println("exception.getMessage() = " + exception.getMessage());
            assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ALREADY_LIKED_ARTICLE);
        }
    }

    @Test
    @DisplayName("게시글에 눌렀던 좋아요 취소하기")
    void cancelArticleLike() {
        //given
        User user = getUser(1L);
        when(userRepository.existsById(user.getId())).thenReturn(true);
        Article article = getArticle(1L);
        when(articleRepository.existsById(article.getId())).thenReturn(true);

        ArticleLike articleLike = new ArticleLike(1L, user, article);
        when(articleLikeRepository.findByArticleIdAndUserId(user.getId(), article.getId())).thenReturn(Optional.of(articleLike));
        //when, then
        assertThat(articleLikeService.cancelArticleLike(article.getId(), user.getId())).isTrue();
    }

    @Test
    @DisplayName("좋아요를 누르지 않은 게시글에 좋아요 취소 요청을 보낸 경우")
    void cancelArticleLikeWhenNotGiveLike() {
        //given
        User user = getUser(1L);
        when(userRepository.existsById(user.getId())).thenReturn(true);
        Article article = getArticle(1L);
        when(articleRepository.existsById(article.getId())).thenReturn(true);

        when(articleLikeRepository.findByArticleIdAndUserId(user.getId(), article.getId())).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(
                CustomException.class,
                () -> articleLikeService.cancelArticleLike(article.getId(), user.getId()));
        //then
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ARTICLE_NOT_LIKED);
    }
}