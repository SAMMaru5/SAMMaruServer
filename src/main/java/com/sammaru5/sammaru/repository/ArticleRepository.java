package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "select a from ArticleEntity a join fetch a.user where a.board = :board",
            countQuery = "select count(a) from ArticleEntity a where a.board = :board")
    List<Article> findByBoard(@Param("board") Board board, Pageable pageable);

    @Query(value = "select a from ArticleEntity a join fetch a.user where a.board = :board",
            countQuery = "select count(a) from ArticleEntity a where a.board = :board")
    List<Article> findByBoard(@Param("board") Board board);

    @Query(value = "select a from ArticleEntity a join fetch a.files where a.id = :id")
    Optional<Article> findArticleWithFile(@Param("id") Long articleId);

    @Transactional
    @Modifying
    @Query(value = "delete from ArticleEntity a where a.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
