package ru.zheleznov.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zheleznov.impl.models.Room;
import ru.zheleznov.impl.models.Schedule;

import java.util.Date;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "SELECT EXISTS (SELECT * FROM schedule WHERE (:start <= date_end)  and  (:end >= date_start) AND auditorium_id=:id)", nativeQuery = true)
    Boolean isScheduleOverlaps(@Param("start") Date start, @Param("end") Date end, @Param("id") Room room);
}
