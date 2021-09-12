package ru.zheleznov.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zheleznov.api.dto.RequestResult;
import ru.zheleznov.api.dto.TalkDto;
import ru.zheleznov.api.forms.TalkCreateForm;
import ru.zheleznov.api.services.TalkService;
import ru.zheleznov.impl.models.Room;
import ru.zheleznov.impl.models.Schedule;
import ru.zheleznov.impl.models.Talk;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.RoomRepository;
import ru.zheleznov.impl.repositories.ScheduleRepository;
import ru.zheleznov.impl.repositories.TalkRepository;
import ru.zheleznov.impl.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TalkServiceImpl implements TalkService {

    private final TalkRepository talkRepository;

    private final ModelMapper modelMapper;

    private final ScheduleRepository scheduleRepository;

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    @Autowired
    public TalkServiceImpl(TalkRepository talkRepository, ModelMapper modelMapper, ScheduleRepository scheduleRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.talkRepository = talkRepository;
        this.modelMapper = modelMapper;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<TalkDto> getAllTalk() {
        return talkRepository.findAll()
                .stream().map(talk -> modelMapper.map(talk, TalkDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RequestResult<TalkDto> save(TalkCreateForm talkForm) {

        Talk talk = Talk.builder()
                .title(talkForm.getTitle())
                .build();

        Set<User> speakers = getSpeakers(talkForm.getSpeakersId());

        if (speakers == null) {
            return new RequestResult<>(
                    Optional.empty(),"Один или несколько спикеров не найдены");
        }

        talk.setSpeakers(speakers);

        Optional<Room> room = roomRepository.findById(talkForm.getAuditoriumId());

        if (!room.isPresent()) {
            return new RequestResult<>(
                    Optional.empty(),"Такая аудитория не найдена");
        }

        Schedule schedule = Schedule.builder()
                .room(room.get())
                .dateStart(talkForm.getDateStart())
                .dateEnd(talkForm.getDateEnd())
                .build();

        Schedule scheduleFromDb = checkAndSaveSchedule(schedule);

        if (scheduleFromDb == null) {
            return new RequestResult<>(
                    Optional.empty(),"Время в этой аудитории уже занято");
        }

        talk.setSchedule(schedule);

        Talk talkFromDb = talkRepository.save(talk);

        return new RequestResult<>(
                Optional.of(modelMapper.map(talkFromDb, TalkDto.class))
                ,"Доклад успешно добавлен");
    }

    private Schedule checkAndSaveSchedule(Schedule schedule) {
        if (scheduleRepository.isScheduleOverlaps(schedule.getDateStart(), schedule.getDateEnd(), schedule.getRoom())) {
            return null;
        }

        return scheduleRepository.save(schedule);
    }

    private Set<User> getSpeakers(Set<Long> speakersId) {
        Set<User> users = new HashSet<>();

        for (Long id: speakersId) {
            Optional<User> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                users.add(optionalUser.get());
            } else {
                return null;
            }
        }

        return users;
    }
}
