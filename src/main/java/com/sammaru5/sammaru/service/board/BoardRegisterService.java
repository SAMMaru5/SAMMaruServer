package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.request.BoardRequest;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.exception.AlreadyExistBoardnameException;
import com.sammaru5.sammaru.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardRegisterService {

    private final BoardRepository boardRepository;

    public BoardEntity addBoard(BoardRequest boardRequest) throws Exception {
        String boardname = boardRequest.getBoardname();
        BoardEntity findBoard = boardRepository.findByBoardname(boardname);
        if(findBoard == null) {
            return boardRepository.save(new BoardEntity(boardRequest));
        } else {
            throw new AlreadyExistBoardnameException();
        }
    }
}
