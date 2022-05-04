package com.sammaru5.sammaru.service.schedule;

import com.sammaru5.sammaru.domain.ScheduleEntity;
import com.sammaru5.sammaru.dto.ScheduleDTO;
import com.sammaru5.sammaru.repository.ScheduleRepository;
import com.sammaru5.sammaru.request.ScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ScheduleRegisterService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleDTO addSchedule(ScheduleRequest calendarRequest) {
        ScheduleEntity schedule = new ScheduleEntity(calendarRequest);
        scheduleRepository.save(schedule);
        return new ScheduleDTO(schedule);
    }
}
