package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    void deleteAllByUserId(Long userId);

    void deleteAllByArticleId(Long articleId);

    boolean existsByArticleIdAndUserId(Long articleId, Long userId);

    Optional<ArticleLike> findByArticleIdAndUserId(Long articleId, Long userId);
}
