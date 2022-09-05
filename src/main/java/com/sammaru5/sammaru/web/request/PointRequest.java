package com.sammaru5.sammaru.web.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PointRequest {
    @NotNull(message = "부여할 점수를 입력해주세요")
    Long addPoint;
}
