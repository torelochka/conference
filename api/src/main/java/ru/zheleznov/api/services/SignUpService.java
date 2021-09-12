package ru.zheleznov.api.services;

import ru.zheleznov.api.dto.RequestResult;
import ru.zheleznov.api.dto.UserDto;
import ru.zheleznov.api.forms.SignUpForm;

public interface SignUpService {
    RequestResult<UserDto> signUp(SignUpForm signUpForm);
}
