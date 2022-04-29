package com.sammaru5.sammaru.dto;

import com.sammaru5.sammaru.domain.ScheduleEntity;
import lombok.Getter;

import java.sql.Date;

@Getter
public class ScheduleDTO {

    private Long id;
    private String title;
    private Date start;
    private Date end;
    private String content;

    public ScheduleDTO(ScheduleEntity calendar) {
        this.id = calendar.getId();
        this.title = calendar.getTitle();
        this.start = calendar.getStart();
        this.end = calendar.getEnd();
        this.content = calendar.getContent();
    }
}