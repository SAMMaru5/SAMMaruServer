package com.sammaru5.sammaru.controller.calendar;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.domain.CalendarEntity;
import com.sammaru5.sammaru.dto.CalendarDTO;
import com.sammaru5.sammaru.request.CalendarRequest;
import com.sammaru5.sammaru.service.calendar.CalendarRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Calendar;

@RequiredArgsConstructor
@Api(tags = {"달력 API"})
@RestController
public class CalendarController {

    private final CalendarRegisterService calendarRegisterService;

    @PostMapping(value = "/api/calendars")
    @ApiOperation(value = "일정 생성", notes = "달력에 일정 추가", response = CalendarDTO.class)
    public ApiResult<?> calendarAdd(Authentication authentication, @Valid @RequestBody CalendarRequest calendarRequest) {
        return ApiResult.OK(calendarRegisterService.addCalendar(calendarRequest));
    }

}
