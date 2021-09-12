package ru.zheleznov.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.zheleznov.api.dto.SignUpResult;
import ru.zheleznov.api.dto.UserDto;
import ru.zheleznov.api.forms.SignUpForm;
import ru.zheleznov.api.services.SignUpService;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.UserRepository;

import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public SignUpResult signUp(SignUpForm signUpForm) {
        if (userRepository.findByEmail(signUpForm.getEmail()).isPresent()) {
            return SignUpResult.builder()
                    .user(Optional.empty())
                    .message("Почта уже занята")
                    .build();
        }

        User user = User.builder()
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();

        UserDto userDto = modelMapper.map(userRepository.save(user), UserDto.class);

        return SignUpResult.builder()
                .user(Optional.of(userDto))
                .message("Регистрация прошла успешно")
                .build();
    }
}
