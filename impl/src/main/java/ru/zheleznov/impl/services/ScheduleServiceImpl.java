package ru.zheleznov.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zheleznov.api.dto.ScheduleDto;
import ru.zheleznov.api.services.ScheduleService;
import ru.zheleznov.impl.repositories.ScheduleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ScheduleDto> getAllSchedules() {
        return scheduleRepository.findAll()
                .stream().map(schedule -> modelMapper.map(schedule, ScheduleDto.class))
                .collect(Collectors.toList());
    }
}
