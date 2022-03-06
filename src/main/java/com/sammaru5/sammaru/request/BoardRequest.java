package com.sammaru5.sammaru.request;

import lombok.Getter;

@Getter
public class BoardRequest {
    private String boardname;
    private String description;

    public BoardRequest(String boardname, String description) {
        this.boardname = boardname;
        this.description = description;
    }
}
