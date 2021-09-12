package ru.zheleznov.api.services;

import ru.zheleznov.api.dto.UserDeleteResult;
import ru.zheleznov.api.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> userByEmail(String email);
    Optional<UserDto> userById(Long id);

    UserDeleteResult deleteUser(Long id);
    void updateUser(Long id);

    // TODO: 12.09.2021 выводить с пагиначией
    List<UserDto> getAllUsers();
}
