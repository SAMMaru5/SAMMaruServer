package com.sammaru5.sammaru.service.schedule;

import com.sammaru5.sammaru.domain.Schedule;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ScheduleRepository;
import com.sammaru5.sammaru.web.dto.ScheduleDTO;
import com.sammaru5.sammaru.web.request.ScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ScheduleModifyService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleDTO modifySchedule(Long scheduleId, ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND, scheduleId.toString()));

        schedule.modifySchedule(scheduleRequest);
        return ScheduleDTO.from(schedule);
    }
}
