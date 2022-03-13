package com.sammaru5.sammaru.dto;

import com.sammaru5.sammaru.domain.BoardEntity;

public class BoardDTO {

    private Long boardId;
    private String boardname;
    private String description;

    public BoardDTO(BoardEntity board) {
        this.boardname = board.getBoardname();;
        this.description = board.getDescription();
    }
}
