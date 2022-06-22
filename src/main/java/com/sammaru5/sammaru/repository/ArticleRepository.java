package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.BoardEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    @Query(value = "select a from ArticleEntity a join fetch a.user where a.board = :board",
            countQuery = "select count(a) from ArticleEntity a where a.board = :board")
    List<ArticleEntity> findByBoard(@Param("board") BoardEntity board, Pageable pageable);

    @Query(value = "select a from ArticleEntity a join fetch a.user where a.board = :board",
            countQuery = "select count(a) from ArticleEntity a where a.board = :board")
    List<ArticleEntity> findByBoard(@Param("board") BoardEntity board);

    @Query(value = "select a from ArticleEntity a join fetch a.files where a.id = :id")
    Optional<ArticleEntity> findArticleWithFile(@Param("id") Long articleId);

    @Transactional
    @Modifying
    @Query(value = "delete from ArticleEntity a where a.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
