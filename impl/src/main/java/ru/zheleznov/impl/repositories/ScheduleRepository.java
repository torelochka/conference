package ru.zheleznov.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zheleznov.impl.models.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
