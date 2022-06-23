package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.BoardEntity;
import lombok.Getter;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
public class BoardDTO {
    private Long id;
    private String name;
    private String description;

    public BoardDTO(BoardEntity boardEntity) {
        this.id = boardEntity.getId();
        this.name = boardEntity.getName();
        this.description = boardEntity.getDescription();
    }
}
