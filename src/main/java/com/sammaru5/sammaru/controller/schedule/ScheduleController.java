package com.sammaru5.sammaru.controller.schedule;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.dto.ScheduleDTO;
import com.sammaru5.sammaru.request.ScheduleRequest;
import com.sammaru5.sammaru.service.schedule.ScheduleRegisterService;
import com.sammaru5.sammaru.service.schedule.ScheduleSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

@RequiredArgsConstructor
@Api(tags = {"달력 API"})
@RestController
public class ScheduleController {

    private final ScheduleRegisterService scheduleRegisterService;
    private final ScheduleSearchService scheduleSearchService;

    @PostMapping(value = "/api/schedules")
    @ApiOperation(value = "일정 생성", notes = "달력에 일정 추가", response = ScheduleDTO.class)
    public ApiResult<?> scheduleAdd(Authentication authentication, @Valid @RequestBody ScheduleRequest calendarRequest) {
        return ApiResult.OK(scheduleRegisterService.addSchedule(calendarRequest));
    }

    @GetMapping("/no-permit/schedules")
    @ApiOperation(value = "일정 목록 보기", notes = "Start부터 End시점까지의 일정 보기", response = ScheduleDTO.class)
    public ApiResult<?> scheduleList(@RequestParam Date start, @RequestParam Date end) {
        return ApiResult.OK(scheduleSearchService.findSchedulesFromStartToEnd(start, end));
    }

}
