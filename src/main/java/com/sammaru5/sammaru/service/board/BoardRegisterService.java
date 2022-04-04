package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.dto.BoardDTO;
import com.sammaru5.sammaru.exception.AlreadyExistBoardnameException;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.request.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class BoardRegisterService {
    private final BoardRepository boardRepository;

    public BoardDTO addBoard(BoardRequest boardRequest) throws AlreadyExistBoardnameException {
        String boardName = boardRequest.getName();
        BoardEntity findBoard = boardRepository.findByName(boardName);

        if (findBoard == null) {
            return new BoardDTO(boardRepository.save(BoardEntity.builder()
                    .name(boardRequest.getName())
                    .description(boardRequest.getDescription())
                    .build()));
        } else {
            throw new AlreadyExistBoardnameException();
        }
    }
}
