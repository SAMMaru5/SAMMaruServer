package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleStatusService {

    private final ArticleRepository articleRepository;

    public Optional<ArticleEntity> findArticleById(Long articleId) {
        return articleRepository.findById(articleId);
    }
}
