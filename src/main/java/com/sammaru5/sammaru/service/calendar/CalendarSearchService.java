package com.sammaru5.sammaru.service.calendar;

import com.sammaru5.sammaru.domain.CalendarEntity;
import com.sammaru5.sammaru.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class CalendarSearchService {

    private final CalendarRepository calendarRepository;

    public List<CalendarEntity> findCalendars() {
        return calendarRepository.findAll();
    }
}
