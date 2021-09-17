package ru.zheleznov.api.services;

import ru.zheleznov.api.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<UserDto> userById(Long id);

    void deleteUser(Long id);
    void updateUser(Long id);

    // TODO: 12.09.2021 выводить с пагиначией
    List<UserDto> getAllUsers();

    void confirmEmail(UUID id);

    List<UserDto> getAllSpeakers();
}
