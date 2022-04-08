package com.sammaru5.sammaru.service.schedule;

import com.sammaru5.sammaru.domain.ScheduleEntity;
import com.sammaru5.sammaru.dto.ScheduleDTO;
import com.sammaru5.sammaru.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ScheduleSearchService {

    private final ScheduleRepository scheduleRepository;

    public List<ScheduleDTO> findSchedulesFromStartToEnd(Date start, Date end) {
        List<ScheduleEntity> schedules = scheduleRepository.findByEndBetween(start, end);
        return schedules.stream().map(ScheduleDTO::new).collect(Collectors.toList());
    }
}
