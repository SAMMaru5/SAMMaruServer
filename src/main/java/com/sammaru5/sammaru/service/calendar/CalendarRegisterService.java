package com.sammaru5.sammaru.service.calendar;

import com.sammaru5.sammaru.domain.CalendarEntity;
import com.sammaru5.sammaru.dto.CalendarDTO;
import com.sammaru5.sammaru.repository.CalendarRepository;
import com.sammaru5.sammaru.request.CalendarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarRegisterService {

    private final CalendarRepository calendarRepository;

    public CalendarDTO addCalendar(CalendarRequest calendarRequest) {
        CalendarEntity calendar = new CalendarEntity(calendarRequest);
        calendarRepository.save(calendar);
        return new CalendarDTO(calendar);
    }
}
