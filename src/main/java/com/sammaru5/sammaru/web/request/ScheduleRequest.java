package com.sammaru5.sammaru.web.request;

import lombok.Getter;

import java.sql.Date;

@Getter
public class ScheduleRequest {
    private String title;
    private String content;
    private Date start;
    private Date end;
}
