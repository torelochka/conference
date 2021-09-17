package ru.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zheleznov.api.dto.RoomDto;
import ru.zheleznov.api.services.RoomService;

import java.util.List;

@RestController
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public List<RoomDto> getRooms() {
        return roomService.getAllRooms();
    }
}
