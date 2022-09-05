package com.sammaru5.sammaru.web.request;

import lombok.Getter;
import javax.validation.constraints.NotNull;

@Getter
public class GenerationRequest {

    @NotNull(message = "기수를 입력해주세요")
    private Integer generation;
}
