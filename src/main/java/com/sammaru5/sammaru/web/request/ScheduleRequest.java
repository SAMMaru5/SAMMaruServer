package com.sammaru5.sammaru.web.request;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Getter
public class ScheduleRequest {
    @NotBlank(message = "제목은 필수 입력입니다")
    private String title;

    @NotBlank(message = "내용은 필수 입력입니다")
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;
}
