package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @RequiredArgsConstructor
public class ArticleStatusService {
    private final ArticleRepository articleRepository;

    public Optional<ArticleEntity> findArticle(Long articleId) {
        return articleRepository.findById(articleId);
    }
}
