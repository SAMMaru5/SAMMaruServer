package com.sammaru5.sammaru.service.calendar;

import com.sammaru5.sammaru.domain.CalendarEntity;
import com.sammaru5.sammaru.dto.CalendarDTO;
import com.sammaru5.sammaru.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CalendarSearchService {

    private final CalendarRepository calendarRepository;

    public List<CalendarDTO> findCalendarsFromStartToEnd(Date start, Date end) {
        List<CalendarEntity> schedules = calendarRepository.findByEndBetween(start, end);
        return schedules.stream().map(CalendarDTO::new).collect(Collectors.toList());
    }
}
