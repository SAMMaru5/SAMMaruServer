package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.web.request.BoardRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String name;
    private String description;

    public Board(BoardRequest boardRequest) {
        this.name = boardRequest.getBoardName();
        this.description = boardRequest.getDescription();
    }

}
