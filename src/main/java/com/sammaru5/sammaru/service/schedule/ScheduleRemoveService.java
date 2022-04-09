package com.sammaru5.sammaru.service.schedule;

import com.sammaru5.sammaru.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduleRemoveService {

    private final ScheduleRepository scheduleRepository;

    public boolean removeScheduleById(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
        return true;
    }
}
