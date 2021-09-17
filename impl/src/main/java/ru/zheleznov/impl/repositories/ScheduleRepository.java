package ru.zheleznov.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zheleznov.impl.models.Room;
import ru.zheleznov.impl.models.Schedule;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "SELECT EXISTS (SELECT * FROM schedule WHERE (:start <= date_end)  and  (:end >= date_start) AND room_id=:id)", nativeQuery = true)
    Boolean isScheduleOverlaps(@Param("start") Date start, @Param("end") Date end, @Param("id") Room room);

    @Query(value = "SELECT EXISTS (SELECT * FROM schedule WHERE (:start <= date_end)  and  (:end >= date_start) AND room_id=:room_id AND id != :schedule_id)", nativeQuery = true)
    Boolean isScheduleOverlapsWithoutExactlySchedule(@Param("start") Date start, @Param("end") Date end,
                                                    @Param("room_id") Room room, @Param("schedule_id") Schedule schedule);

    @Query(value = "SELECT * FROM schedule WHERE talk_id=:id", nativeQuery = true)
    Optional<Schedule> findByTalkId(@Param("id") Long id);

    List<Schedule> findAllByOrderByRoomIdAsc();
}
