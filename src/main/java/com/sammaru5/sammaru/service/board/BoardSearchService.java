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
public class BoardSearchService {

    private final BoardRepository boardRepository;

    /**
     * 게시판 단건 조회
     * @param id
     * @return
     * @throws Exception
     */
    public BoardEntity findBoardById(Long id) throws Exception {
        Optional<BoardEntity> findBoard = boardRepository.findById(id);
        if(findBoard.isPresent()) {
            return findBoard.get();
        } else {
            throw new NonExistentBoardException();
        }
    }

    /**
     * boardname을 통해 해당 이름을 가진 게시판을 반환
     * @param boardname
     * @return
     * @throws Exception
     */
    public BoardEntity findBoardByBoardname(String boardname) throws Exception {
        BoardEntity findBoard = boardRepository.findByBoardname(boardname);
        if(findBoard != null) {
            return findBoard;
        } else {
            throw new NonExistentBoardnameException();
        }
    }

    /**
     * 게시판 목록 조회
     * @return
     */
    public List<BoardEntity> findBoardList() {
        List<BoardEntity> boards = boardRepository.findAll();
        if(boards != null) {
            return boards;
        } else {
            throw new NonExistentBoardException();
        }
    }
}
