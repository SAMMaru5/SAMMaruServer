package com.sammaru5.sammaru.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter @AllArgsConstructor
public class CalendarRequest {

    private String title;
    private String content;
    private Date start;
    private Date end;
}
