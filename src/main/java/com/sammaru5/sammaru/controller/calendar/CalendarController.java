package com.sammaru5.sammaru.controller.calendar;

import com.sammaru5.sammaru.apiresult.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {

    @GetMapping("/api/calendars")
    public ApiResult<?> calendarList() {

    }
}
