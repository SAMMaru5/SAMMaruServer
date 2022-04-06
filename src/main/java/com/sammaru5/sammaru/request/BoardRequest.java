package com.sammaru5.sammaru.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class BoardRequest {
    @NotBlank(message = "제목은 필수 입력입니다")
    private String name;
    @NotBlank(message = "설명은 필수 입력입니다")
    private String description;
}
