package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.controller.board.BoardRequest;
import com.sammaru5.sammaru.domain.Board;
import com.sammaru5.sammaru.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardRegisterService {

    private final BoardRepository boardRepository;

    public Board addBoard(BoardRequest boardRequest) throws Exception {
        String boardname = boardRequest.getBoardname();
        Board findBoard = boardRepository.findByBoardname(boardname);
        if(findBoard == null) {
            return boardRepository.save(new Board(boardRequest));
        } else {
            throw new AlreadyExistBoardnameException();
        }
    }
}
