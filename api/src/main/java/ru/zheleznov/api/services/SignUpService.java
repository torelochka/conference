package ru.zheleznov.api.services;

import ru.zheleznov.api.forms.SignUpForm;

public interface SignUpService {
    Boolean signUp(SignUpForm signUpForm);
}
