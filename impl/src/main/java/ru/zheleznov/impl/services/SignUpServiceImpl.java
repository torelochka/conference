package ru.zheleznov.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.zheleznov.api.dto.SignUpResult;
import ru.zheleznov.api.dto.UserDto;
import ru.zheleznov.api.forms.SignUpForm;
import ru.zheleznov.api.services.MailService;
import ru.zheleznov.api.services.SignUpService;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final MailService mailService;

    private final ExecutorService executorService;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, MailService mailService, ExecutorService executorService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.mailService = mailService;
        this.executorService = executorService;
    }

    @Override
    public SignUpResult signUp(SignUpForm signUpForm) {
        if (userRepository.findByEmail(signUpForm.getEmail()).isPresent()) {
            return SignUpResult.builder()
                    .user(Optional.empty())
                    .message("Почта уже занята")
                    .build();
        }

        UUID confirmedCode = UUID.randomUUID();

        User user = User.builder()
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .confirmedCode(confirmedCode)
                .build();

        UserDto userDto = modelMapper.map(userRepository.save(user), UserDto.class);

        executorService.submit(() -> {
            mailService.sendEmailForConfirm(user.getEmail(), confirmedCode.toString());
        });

        return SignUpResult.builder()
                .user(Optional.of(userDto))
                .message("Регистрация прошла успешно")
                .build();
    }
}
