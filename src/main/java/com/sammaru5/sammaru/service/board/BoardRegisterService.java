package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.web.dto.BoardDTO;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.web.request.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardRegisterService {
    private final BoardRepository boardRepository;

    @Transactional
    public BoardDTO addBoard(BoardRequest boardRequest) {
        String boardName = boardRequest.getBoardName();

        if(boardRepository.existsByBoardName(boardRequest.getBoardName())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_BOARD, boardRequest.getBoardName());
        }

        return BoardDTO.from(boardRepository.save(boardRequest.toEntity()));
    }
}
