package ru.zheleznov.api.services;

import ru.zheleznov.api.dto.RoomDto;

import java.util.List;

public interface RoomService {
    List<RoomDto> getAllRooms();
}
