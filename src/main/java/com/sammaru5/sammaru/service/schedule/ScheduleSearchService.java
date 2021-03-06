package com.sammaru5.sammaru.service.schedule;

import com.sammaru5.sammaru.domain.Schedule;
import com.sammaru5.sammaru.web.dto.ScheduleDTO;
import com.sammaru5.sammaru.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service @RequiredArgsConstructor
public class ScheduleSearchService {

    private final ScheduleRepository scheduleRepository;

    public List<ScheduleDTO> findSchedulesFromStartToEnd(Date start, Date end) {
        List<Schedule> schedules = scheduleRepository.findByEndBetweenOrderByStartAscEndAsc(start, end);
        return schedules.stream().map(ScheduleDTO::new).collect(Collectors.toList());
    }
}
