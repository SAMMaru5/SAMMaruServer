package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByBoardName(String boardName);

    boolean existsByBoardName(String boardName);
}
