package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.web.request.BoardRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor
public class BoardEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String description;

    public BoardEntity(BoardRequest boardRequest) {
        this.name = boardRequest.getBoardName();
        this.description = boardRequest.getDescription();
    }

}
