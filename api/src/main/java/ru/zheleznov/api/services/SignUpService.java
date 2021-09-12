package ru.zheleznov.api.services;

import ru.zheleznov.api.dto.SignUpResult;
import ru.zheleznov.api.dto.UserDto;
import ru.zheleznov.api.forms.SignUpForm;

import java.util.Optional;

public interface SignUpService {
    SignUpResult signUp(SignUpForm signUpForm);
}
