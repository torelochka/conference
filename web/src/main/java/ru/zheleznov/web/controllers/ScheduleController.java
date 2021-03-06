package ru.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.zheleznov.api.dto.ScheduleDto;
import ru.zheleznov.api.services.ScheduleService;

import java.util.List;

@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule")
    public List<ScheduleDto> allSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/schedule/{talkId}")
    public ResponseEntity<?> scheduleByTalkId(@PathVariable Long talkId) {
        ScheduleDto schedule = scheduleService.getScheduleByTalkId(talkId);

        return ResponseEntity.ok(schedule);
    }
}
