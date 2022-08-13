package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByBoardName(String boardName);

    @Query(value = "select b from Board b where b.boardName = :boardName")
    Optional<Board> findByBoardNameNotice(@Param("boardName") String boardName);
}
