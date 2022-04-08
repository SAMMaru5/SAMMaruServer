package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.request.ScheduleRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "schedule")
@Getter
@NoArgsConstructor
public class ScheduleEntity {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private Date start;
    private Date end;
    private String content;

    public ScheduleEntity(ScheduleRequest scheduleRequest) {
        this.title = scheduleRequest.getTitle();
        this.start = scheduleRequest.getStart();
        this.end = scheduleRequest.getEnd();
        this.content = scheduleRequest.getContent();
    }
}
