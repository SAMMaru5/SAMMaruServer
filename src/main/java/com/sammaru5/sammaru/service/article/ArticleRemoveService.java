package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.service.user.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service @RequiredArgsConstructor
public class ArticleRemoveService {
    private final ArticleRepository articleRepository;
    private final ArticleSearchService articleSearchService;
    private final UserStatusService userStatusService;

    @CacheEvict(keyGenerator = "articleCacheKeyGenerator", value = "article", cacheManager = "cacheManager")
    public boolean removeArticle(Long articleId, User findUser, Long boardId) throws CustomException {
        Optional<Article> findArticle = articleRepository.findById(articleId);
        if(findArticle.isPresent()) {
            if(findArticle.get().getUser() != findUser){ //작성자가 아닌 사람이 접근하려고 할때때
                throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS, findUser.getId().toString());
            }
            articleRepository.deleteById(findArticle.get().getId());
            return true;
        } else {
            throw new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString());
        }
    }

    public boolean removeArticleByAdmin(Long boardId) throws CustomException {
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

