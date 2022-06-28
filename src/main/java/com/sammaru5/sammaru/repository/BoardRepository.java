package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByBoardName(String boardName);
}
