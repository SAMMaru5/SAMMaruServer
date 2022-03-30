package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {
}
