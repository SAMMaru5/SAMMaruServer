package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.web.dto.BoardDTO;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.web.request.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service @RequiredArgsConstructor
public class BoardRegisterService {
    private final BoardRepository boardRepository;

    public BoardDTO addBoard(BoardRequest boardRequest) throws IllegalArgumentException {
        String boardName = boardRequest.getName();
        Optional<BoardEntity> boardEntity = boardRepository.findByName(boardName);

        if (!boardEntity.isPresent()) {
            return new BoardDTO(boardRepository.save(BoardEntity.builder()
                    .name(boardRequest.getName())
                    .description(boardRequest.getDescription())
                    .build()));
        } else {
            throw new IllegalArgumentException("해당 게시판 이름은 이미 존재합니다!");
        }
    }
}
