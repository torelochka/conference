package ru.zheleznov.api.services;

import ru.zheleznov.api.dto.JwtResponse;
import ru.zheleznov.api.forms.LoginRequest;

public interface JwtLoginService {
    JwtResponse signIn(LoginRequest loginRequest) ;
}
