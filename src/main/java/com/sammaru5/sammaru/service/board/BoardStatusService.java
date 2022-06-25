package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional(readOnly = true)
@Service @RequiredArgsConstructor
public class BoardStatusService {
    private final BoardRepository boardRepository;

    public BoardEntity findBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시판입니다."));
    }

    public BoardEntity findByBoardName(String boardName) {
        List<BoardEntity> boards = boardRepository.findByBoardName(boardName);
        if(boards.isEmpty()) {
           throw new IllegalArgumentException("존재하지 않는 게시판입니다.");
        }
        return boards.get(0);
    }

    public List<BoardEntity> findBoards() {
        return boardRepository.findAll();
    }
}
