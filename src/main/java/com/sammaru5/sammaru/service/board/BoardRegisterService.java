package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.web.dto.BoardDTO;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.web.request.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@Service @RequiredArgsConstructor
public class BoardRegisterService {
    private final BoardRepository boardRepository;

    public BoardDTO addBoard(BoardRequest boardRequest) {
        validateDuplicateBoard(boardRequest.getBoardName());
        return new BoardDTO(boardRepository.save(boardRequest.toEntity()));
    }

    private void validateDuplicateBoard(String boardName) {
        List<BoardEntity> boards = boardRepository.findByBoardName(boardName);
        if(!boards.isEmpty()) throw new IllegalStateException("중복된 게시판입니다.");
    }
}
