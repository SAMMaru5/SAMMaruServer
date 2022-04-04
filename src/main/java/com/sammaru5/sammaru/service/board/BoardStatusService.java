package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.exception.NonExistentBoardException;
import com.sammaru5.sammaru.exception.NonExistentBoardnameException;
import com.sammaru5.sammaru.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class BoardStatusService {
    private final BoardRepository boardRepository;

    public BoardEntity findBoard(Long boardId) throws NonExistentBoardException {
        Optional<BoardEntity> findBoard = boardRepository.findById(boardId);
        if(findBoard.isPresent()) {
            return findBoard.get();
        } else {
            throw new NonExistentBoardException();
        }
    }

    public BoardEntity findBoardByName(String boardName) throws NonExistentBoardnameException {
        BoardEntity findBoard = boardRepository.findByBoardname(boardName);
        if(findBoard != null) {
            return findBoard;
        } else {
            throw new NonExistentBoardnameException();
        }
    }

    public List<BoardEntity> findBoards() throws NonExistentBoardException{
        List<BoardEntity> boards = boardRepository.findAll();
        if(boards != null) {
            return boards;
        } else {
            throw new NonExistentBoardException();
        }
    }
}
