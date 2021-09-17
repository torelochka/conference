package ru.zheleznov.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zheleznov.api.dto.UserDto;
import ru.zheleznov.api.services.TalkService;
import ru.zheleznov.api.services.UserService;
import ru.zheleznov.impl.models.Talk;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.TalkRepository;
import ru.zheleznov.impl.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TalkRepository talkRepository;

    private final TalkService talkService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TalkRepository talkRepository, TalkService talkService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.talkRepository = talkRepository;
        this.talkService = talkService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<UserDto> userById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public void deleteUser(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("User not found");
        }

        User dbUser = User.builder().id(id).build();

        List<Talk> allBySpeakers = talkRepository.findAllBySpeakers(dbUser);

        for (Talk talk : allBySpeakers) {
            Set<User> speakers = talk.getSpeakers();
            speakers.removeIf(speaker -> speaker.getId().equals(id));
            if (speakers.isEmpty()) {
                talkService.deleteTalk(talk.getId());
            } else  {
                talkRepository.save(talk);
            }
        }

        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("User not found");
        }

        User user = optionalUser.get();
        user.setRole(User.Role.SPEAKER);

        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream().map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void confirmEmail(UUID id) {
        Optional<User> optionalUser = userRepository.findByConfirmedCode(id);
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("User not found");
        }

        User user = optionalUser.get();
        user.setState(User.State.STATE_ACTIVE);

        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllSpeakers() {
        return userRepository.findAllByRole(User.Role.SPEAKER)
                .stream().map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}
