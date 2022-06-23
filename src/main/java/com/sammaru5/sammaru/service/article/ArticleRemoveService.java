package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.service.user.UserStatusService;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.AccessDeniedException;
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
    public boolean removeArticle(Long articleId, UserEntity findUser, Long boardId) throws AccessDeniedException, NullPointerException {
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다."));

        if (article.getUser() != findUser) { //작성자가 아닌 사람이 접근하려고 할때때
            throw new AccessDeniedException("해당 게시물에 권한이 없는 사용자 입니다");
        }

        articleRepository.delete(article);
        return true;
    }

    public boolean removeArticleByAdmin(Long boardId) throws NullPointerException {
        List<ArticleDTO> articles = articleSearchService.findArticlesByBoardId(boardId);
        if (articles.isEmpty()) {
            throw new NoSuchElementException("해당 게시판에 게시글이 존재하지 않습니다!");
        }

        List<Long> ids = new ArrayList<>();
        for (ArticleDTO a : articles) {
            ids.add(a.getId());
        }
        articleRepository.deleteAllByIdInQuery(ids);
        return true;
    }

}

