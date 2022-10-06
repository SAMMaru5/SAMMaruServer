package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.domain.Board;
import com.sammaru5.sammaru.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardSearchService {
    private final BoardRepository boardRepository;

    public List<Board> findBoards() {
        return boardRepository.findAll();
    }
}
