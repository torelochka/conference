package ru.zheleznov.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zheleznov.api.dto.ScheduleDto;
import ru.zheleznov.api.services.ScheduleService;
import ru.zheleznov.impl.models.Schedule;
import ru.zheleznov.impl.repositories.ScheduleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
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
        return scheduleRepository.findAllByOrderByRoomIdAsc()
                .stream().map(schedule -> modelMapper.map(schedule, ScheduleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDto getScheduleByTalkId(Long talkId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findByTalkId(talkId);
        if (!optionalSchedule.isPresent()) {
            throw new EntityNotFoundException("Schedule not found");
        }

        return modelMapper.map(optionalSchedule.get(), ScheduleDto.class);
    }
}
