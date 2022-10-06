package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.web.request.ScheduleRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@NoArgsConstructor
public class Schedule {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    private String title;
    private Date start;
    private Date end;
    private String content;

    public Schedule(ScheduleRequest scheduleRequest) {
        this.title = scheduleRequest.getTitle();
        this.start = scheduleRequest.getStart();
        this.end = scheduleRequest.getEnd();
        this.content = scheduleRequest.getContent();
    }

    public void modifySchedule(ScheduleRequest scheduleRequest){
        this.title = scheduleRequest.getTitle();
        this.content = scheduleRequest.getContent();
        this.start = scheduleRequest.getStart();
        this.end = scheduleRequest.getEnd();
    }
}
