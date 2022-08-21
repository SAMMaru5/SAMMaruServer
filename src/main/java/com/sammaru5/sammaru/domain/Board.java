package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.web.request.BoardRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String boardName;
    private String description;

    public Board(BoardRequest boardRequest) {
        this.boardName = boardRequest.getBoardName();
        this.description = boardRequest.getDescription();
    }

}
