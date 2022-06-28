package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.Board;
import lombok.Getter;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
public class BoardDTO {
    private Long id;
    private String name;
    private String description;

    public BoardDTO(Board board) {
        this.id = board.getId();
        this.name = board.getBoardName();
        this.description = board.getDescription();
    }
}
