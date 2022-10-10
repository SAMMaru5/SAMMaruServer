package com.sammaru5.sammaru.service.schedule;

import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ScheduleRemoveService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public boolean removeScheduleById(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new CustomException(ErrorCode.SCHEDULE_NOT_FOUND, scheduleId.toString());
        }

        scheduleRepository.deleteById(scheduleId);
        return true;
    }
}
