package ru.zheleznov.api.services;

import ru.zheleznov.api.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> userByEmail(String email);
    Optional<UserDto> userById(Long id);
}
