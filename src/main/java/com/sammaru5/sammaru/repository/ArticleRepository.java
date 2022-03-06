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

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    List<ArticleEntity> findByBoard(BoardEntity board);
    List<ArticleEntity> findByBoard(BoardEntity board, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "delete from ArticleEntity a where a.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
