package com.sammaru5.sammaru.web.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentRequest {
    @NotBlank(message = "내용은 필수 입력입니다")
    private String content;
}
