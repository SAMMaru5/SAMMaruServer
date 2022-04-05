package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class BoardStatusService {
    private final BoardRepository boardRepository;

    public BoardEntity findBoard(Long boardId) throws NullPointerException {
        Optional<BoardEntity> findBoard = boardRepository.findById(boardId);
        if(findBoard.isPresent()) {
            return findBoard.get();
        } else {
            throw new NullPointerException("해당 boardId 게시판이 존재하지 않습니다");
        }
    }

    public BoardEntity findBoardByName(String boardName) throws NullPointerException {
        Optional<BoardEntity> findBoard = boardRepository.findByName(boardName);
        if(findBoard.isPresent()) {
            return findBoard.get();
        } else {
            throw new NullPointerException("해당 boardName 게시판이 존재하지 않습니다!");
        }
    }

    public List<BoardEntity> findBoards() throws NullPointerException{
        List<BoardEntity> boards = boardRepository.findAll();
        if(boards != null) {
            return boards;
        } else {
            throw new NullPointerException("현재 존재하는 게시판이 없습니다");
        }
    }
}
