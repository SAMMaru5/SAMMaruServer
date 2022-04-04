package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.request.CalendarRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "calendar")
@Getter
@NoArgsConstructor
public class CalendarEntity {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private Date start;
    private Date end;
    private String content;

    public CalendarEntity(CalendarRequest calendarRequest) {
        this.title = calendarRequest.getTitle();
        this.start = calendarRequest.getStart();
        this.end = calendarRequest.getEnd();
        this.content = calendarRequest.getContent();
    }
}
