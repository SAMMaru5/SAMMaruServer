package com.sammaru5.sammaru.dto;

import com.sammaru5.sammaru.domain.BoardEntity;
import lombok.Getter;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
public class BoardDTO {

    private Long boardId;
    private String boardname;
    private String description;

    public BoardDTO(BoardEntity boardEntity) {
        copyProperties(boardEntity, this);
        this.boardId = boardEntity.getId();
    }
}
