package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByEndBetweenOrderByStartAscEndAsc(Date start, Date end);
}
