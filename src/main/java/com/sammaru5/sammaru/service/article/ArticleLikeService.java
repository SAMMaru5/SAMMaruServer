package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.ArticleLike;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ArticleLikeRepository;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleLikeService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;

    public Long giveArticleLike(Long articleId, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString()));
        Article findArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString()));

        if (articleLikeRepository.existsByArticleIdAndUserId(articleId, userId)) {
            throw new CustomException(ErrorCode.ALREADY_LIKED_ARTICLE, articleId.toString());
        }

        ArticleLike articleLike = ArticleLike.createArticleLike(findUser, findArticle);
        return articleLikeRepository.save(articleLike).getId();
    }

    public boolean cancelArticleLike(Long articleId, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString());
        }
        if (!articleRepository.existsById(articleId)) {
            throw new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString());
        }
        ArticleLike articleLike = articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_LIKED, articleId.toString()));
        articleLikeRepository.delete(articleLike);
        return true;
    }
}
