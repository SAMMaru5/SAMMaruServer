package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ArticleLikeRepository;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.CommentRepository;
import com.sammaru5.sammaru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ArticleRemoveService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final CommentRepository commentRepository;

    @CacheEvict(keyGenerator = "articleCacheKeyGenerator", value = "article", cacheManager = "cacheManager")
    public boolean removeArticle(Long articleId, Long userId, Long boardId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString()));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString()));

        if (article.getUser() != findUser) { //작성자가 아닌 사람이 접근하려고 할때때
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS, findUser.getId().toString());
        }

        articleLikeRepository.deleteAllByArticleId(articleId);
        commentRepository.deleteAllByArticleId(articleId);

        articleRepository.delete(article);
        return true;
    }

    public boolean removeArticleByAdmin(Long boardId) {
        if (!articleRepository.existsById(boardId)) {
            throw new CustomException(ErrorCode.BOARD_NOT_FOUND, boardId.toString());
        }

        articleRepository.deleteArticlesByBoardId(boardId);
        return true;
    }

}

