package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service @RequiredArgsConstructor
public class ArticleStatusService {
    private final ArticleRepository articleRepository;

    public Optional<ArticleEntity> findArticle(Long articleId) {
        return articleRepository.findById(articleId);
    }
}
