package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.CalendarEntity;
import com.sammaru5.sammaru.dto.CalendarDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {

    List<CalendarEntity> findByEndBetween(Date start, Date end);
}
