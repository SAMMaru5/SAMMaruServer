package com.sammaru5.sammaru.web.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PointRequest {
    @NotBlank(message = "부여할 점수는 필수 입력입니다")
    Long addPoint;
}
