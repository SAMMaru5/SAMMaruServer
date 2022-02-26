package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findByBoardname(String boardname);
}
