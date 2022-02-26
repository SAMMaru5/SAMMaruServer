package com.sammaru5.sammaru.controller.board;

import com.sammaru5.sammaru.domain.Board;

public class BoardDTO {

    private String boardname;
    private String description;

    public BoardDTO(Board board) {
        this.boardname = board.getBoardname();;
        this.description = board.getDescription();
    }
}
