package com.sammaru5.sammaru.service.schedule;

import com.sammaru5.sammaru.domain.Schedule;
import com.sammaru5.sammaru.web.dto.ScheduleDTO;
import com.sammaru5.sammaru.repository.ScheduleRepository;
import com.sammaru5.sammaru.web.request.ScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ScheduleRegisterService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleDTO addSchedule(ScheduleRequest calendarRequest) {
        Schedule schedule = new Schedule(calendarRequest);
        scheduleRepository.save(schedule);
        return ScheduleDTO.from(schedule);
    }
}
