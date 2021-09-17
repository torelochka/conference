package ru.zheleznov.api.services;

import ru.zheleznov.api.dto.ScheduleDto;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDto> getAllSchedules();

    ScheduleDto getScheduleByTalkId(Long talkId);
}
