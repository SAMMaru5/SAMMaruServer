package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.request.BoardRequest;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String description;

    public BoardEntity(BoardRequest boardRequest) {
        this(boardRequest.getName(), boardRequest.getDescription());
    }

    public BoardEntity(String boardName, String description) {
        this.name = boardName;
        this.description = description;
    }

}
