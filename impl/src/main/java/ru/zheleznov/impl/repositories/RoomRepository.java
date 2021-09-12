package ru.zheleznov.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zheleznov.impl.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
