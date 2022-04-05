package com.sammaru5.sammaru.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ArticleRequest {
    @NotBlank(message = "제목은 필수 입력입니다")
    private String title;
    @NotBlank(message = "내용은 필수 입력입니다")
    private String content;
}
