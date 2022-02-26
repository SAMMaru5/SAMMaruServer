package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.controller.board.BoardRequest;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class Board {

    @Id @GeneratedValue
    private Long id;

    private String boardname;
    private String description;

    public Board(BoardRequest boardRequest) {
        this(boardRequest.getBoardname(), boardRequest.getDescription());
    }

    public Board(String boardname, String description) {
        this.boardname = boardname;
        this.description = description;
    }

}
