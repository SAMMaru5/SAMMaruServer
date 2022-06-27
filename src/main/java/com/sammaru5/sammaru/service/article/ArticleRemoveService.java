package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.service.user.UserStatusService;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
@RequiredArgsConstructor
public class ArticleRemoveService {
    private final ArticleRepository articleRepository;
    private final ArticleSearchService articleSearchService;
    private final UserStatusService userStatusService;

    @CacheEvict(keyGenerator = "articleCacheKeyGenerator", value = "article", cacheManager = "cacheManager")
    public boolean removeArticle(Long articleId, UserEntity findUser, Long boardId) {
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString()));

        if (article.getUser() != findUser) { //작성자가 아닌 사람이 접근하려고 할때때
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS, findUser.getId().toString());
        }

        articleRepository.delete(article);
        return true;
    }

    public boolean removeArticleByAdmin(Long boardId) {
        List<ArticleDTO> articles = articleSearchService.findArticlesByBoardId(boardId);
        if (articles.isEmpty()) {
            throw new CustomException(ErrorCode.BOARD_IS_EMPTY, boardId.toString());
        }

        List<Long> ids = new ArrayList<>();
        for (ArticleDTO a : articles) {
            ids.add(a.getId());
        }
        articleRepository.deleteAllByIdInQuery(ids);
        return true;
    }

}

