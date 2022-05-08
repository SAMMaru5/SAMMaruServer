package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.ScheduleEntity;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> findByEndBetweenOrderByStartAscEndAsc(Date start, Date end);
}
