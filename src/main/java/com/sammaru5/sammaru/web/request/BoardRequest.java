package com.sammaru5.sammaru.web.request;

import com.sammaru5.sammaru.domain.Board;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class BoardRequest {
    @NotBlank(message = "제목은 필수 입력입니다")
    private String boardName;
    @NotBlank(message = "설명은 필수 입력입니다")
    private String description;

    public Board toEntity() {
        return new Board(this);
    }
}
