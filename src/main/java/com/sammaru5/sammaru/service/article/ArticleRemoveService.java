package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.dto.ArticleDTO;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.service.user.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
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
    public boolean removeArticle(Long articleId, Authentication authentication, Long boardId) throws AccessDeniedException, NullPointerException {
        UserEntity findUser = userStatusService.getUser(authentication);
        Optional<ArticleEntity> findArticle = articleRepository.findById(articleId);
        if(findArticle.isPresent()) {
            if(findArticle.get().getUser() != findUser){ //작성자가 아닌 사람이 접근하려고 할때때
                throw new AccessDeniedException("해당 게시물에 권한이 없는 사용자 입니다");
            }
            articleRepository.deleteById(findArticle.get().getId());
            return true;
        } else {
            throw new NullPointerException("해당 게시글이 존재하지 않습니다!");
        }
    }

    public boolean removeArticleByAdmin(Long boardId) throws NullPointerException {
        List<ArticleDTO> articles = articleSearchService.findArticlesByBoardId(boardId);
        if (articles.isEmpty()) {
            throw new NullPointerException("해당 게시판에 게시글이 존재하지 않습니다!");
        }
        List<Long> ids = new ArrayList<>();
        for (ArticleDTO a : articles) {
            ids.add(a.getId());
        }
        articleRepository.deleteAllByIdInQuery(ids);
        return true;
    }

}

