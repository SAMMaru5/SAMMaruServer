package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.domain.Board;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.web.dto.BoardDTO;
import com.sammaru5.sammaru.web.request.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardModifyService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardDTO modifyBoard(Long boardId, BoardRequest boardRequest) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND, boardId.toString()));

        board.modifyBoard(boardRequest);
        return BoardDTO.from(board);
    }
}
