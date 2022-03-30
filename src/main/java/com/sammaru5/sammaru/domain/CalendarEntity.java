package com.sammaru5.sammaru.domain;

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
    private Long calendarId;

    private String title;
    private Date start;
    private Date end;
    private String content;
}
