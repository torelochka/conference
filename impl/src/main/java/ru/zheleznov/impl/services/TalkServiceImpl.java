package ru.zheleznov.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zheleznov.api.dto.TalkDto;
import ru.zheleznov.api.dto.UserDto;
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

import javax.persistence.EntityNotFoundException;
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
    public TalkDto save(TalkCreateForm talkForm) {
        Talk talk = prepareSpeakers(talkForm);

        Optional<Room> room = roomRepository.findById(talkForm.getAuditoriumId());

        if (!room.isPresent()) {
            throw new EntityNotFoundException("Room not found");
        }

        Schedule schedule = Schedule.builder()
                .room(room.get())
                .dateStart(talkForm.getDateStart())
                .dateEnd(talkForm.getDateEnd())
                .build();

        if (!checkSchedule(schedule)) {
            throw new IllegalArgumentException("This time in the room is already occupied");
        }

        Talk talkFromDb = talkRepository.save(talk);

        schedule.setTalk(talkFromDb);
        scheduleRepository.save(schedule);

        return modelMapper.map(talkFromDb, TalkDto.class);
    }

    private Talk prepareSpeakers(TalkCreateForm talkForm) {

        Talk talk = Talk.builder()
                .title(talkForm.getTitle())
                .build();

        Set<User> speakers = getSpeakers(talkForm.getSpeakersId());

        if (speakers == null) {
            throw new IllegalArgumentException("One or more speakers were not found or are not speakers");
        }

        talk.setSpeakers(speakers);

        return talk;
    }

    @Override
    public Boolean deleteTalk(Long id, UserDto userDto) {
        Optional<Talk> optionalTalk = talkRepository.findById(id);

        if (optionalTalk.isPresent()) {
            if (optionalTalk.get().getSpeakers()
                    .stream().noneMatch(user -> user.getId().equals(userDto.getId()))) {
                return false;
            }
        }

        Optional<Schedule> schedule = scheduleRepository.findByTalkId(id);
        if (!schedule.isPresent()) {
            return false;
        }

        scheduleRepository.deleteById(schedule.get().getId());

        return true;
    }

    @Override
    public Boolean deleteTalk(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findByTalkId(id);
        if (!schedule.isPresent()) {
            return false;
        }

        scheduleRepository.deleteById(schedule.get().getId());

        return true;
    }

    @Override
    public TalkDto updateTalk(Long id, TalkCreateForm talkCreateForm) {
        Optional<Talk> optionalTalk = talkRepository.findById(id);

        if (!optionalTalk.isPresent()) {
            throw new EntityNotFoundException("Talk not found");
        }

        Talk updateTalk = prepareSpeakers(talkCreateForm);
        Talk talkToUpdate = optionalTalk.get();
        Long updateTalkId = talkToUpdate.getId();

        talkToUpdate = modelMapper.map(updateTalk, Talk.class);
        talkToUpdate.setId(updateTalkId);

        Optional<Room> room = roomRepository.findById(talkCreateForm.getAuditoriumId());
        if (!room.isPresent()) {
            throw new EntityNotFoundException("Room not found");
        }

        Schedule scheduleTalkToUpdate = scheduleRepository.findByTalkId(updateTalkId).get();

        Schedule schedule = Schedule.builder()
                .room(room.get())
                .dateStart(talkCreateForm.getDateStart())
                .dateEnd(talkCreateForm.getDateEnd())
                .build();

        if (scheduleRepository.isScheduleOverlapsWithoutExactlySchedule(
                schedule.getDateStart(), schedule.getDateEnd(), schedule.getRoom(), scheduleTalkToUpdate
        )) {
            throw new IllegalArgumentException("This time in the room is already occupied");
        }

        scheduleTalkToUpdate.setDateStart(talkCreateForm.getDateStart());
        scheduleTalkToUpdate.setDateEnd(talkCreateForm.getDateEnd());

        talkRepository.save(updateTalk);
        scheduleRepository.save(scheduleTalkToUpdate);

        return modelMapper.map(talkToUpdate, TalkDto.class);
    }

    @Override
    public TalkDto getTalk(Long id) {
        Optional<Talk> optionalTalk = talkRepository.findById(id);

        if (!optionalTalk.isPresent()) {
            throw new EntityNotFoundException("Talk not found");
        }

        return modelMapper.map(optionalTalk.get(), TalkDto.class);
    }

    private Boolean checkSchedule(Schedule schedule) {
        return !scheduleRepository.isScheduleOverlaps(
                schedule.getDateStart(), schedule.getDateEnd(), schedule.getRoom());
    }

    private Set<User> getSpeakers(Set<Long> speakersId) {
        Set<User> users = new HashSet<>();

        for (Long id : speakersId) {
            Optional<User> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent() && optionalUser.get().getRole().equals(User.Role.SPEAKER)) {
                users.add(optionalUser.get());
            } else {
                return null;
            }
        }

        return users;
    }
}
