package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.exception.NonExistentAritcleException;
import com.sammaru5.sammaru.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor
public class ArticleRemoveService {

    private final ArticleRepository articleRepository;
    private final ArticleSearchService articleSearchService;

    public boolean removeArticle(Long boardId) throws Exception {
        try {
            List<ArticleEntity> articles = articleSearchService.findAllByBoardId(boardId);
            List<Long> ids = new ArrayList<>();
            for(ArticleEntity a: articles) {
                ids.add(a.getId());
            }
            articleRepository.deleteAllByIdInQuery(ids);
            return true;
        } catch(NonExistentAritcleException e) {
            throw new NonExistentAritcleException();
        } finally {
            return false;
        }
    }
}
