package com.sammaru5.sammaru.service.calendar;

import com.sammaru5.sammaru.domain.CalendarEntity;
import com.sammaru5.sammaru.repository.CalendarRepository;
import com.sammaru5.sammaru.request.CalendarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarRegisterService {

    private final CalendarRepository calendarRepository;

    public boolean addCalendar(CalendarRequest calendarRequest) {
        if (calendarRequest == null || calendarRequest.getTitle().isEmpty() || calendarRequest.getContent().isEmpty() || calendarRequest.getStart() == null || calendarRequest.getEnd() == null) {
            return false;
        } else {
            calendarRepository.save(new CalendarEntity(calendarRequest));
            return true;
        }
    }
}
