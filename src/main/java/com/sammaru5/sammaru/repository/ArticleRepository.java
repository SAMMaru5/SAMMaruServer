package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.BoardEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    List<ArticleEntity> findByBoard(BoardEntity board);
    List<ArticleEntity> findByBoard(BoardEntity board, Pageable pageable);
}
