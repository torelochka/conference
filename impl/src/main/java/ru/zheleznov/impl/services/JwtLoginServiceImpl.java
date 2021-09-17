package ru.zheleznov.impl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.zheleznov.api.dto.JwtResponse;
import ru.zheleznov.api.forms.LoginRequest;
import ru.zheleznov.api.services.JwtLoginService;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.UserRepository;
import ru.zheleznov.impl.utils.JwtUtils;

import java.util.Optional;

@Service
public class JwtLoginServiceImpl implements JwtLoginService {

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JwtLoginServiceImpl(UserRepository userRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtResponse signIn(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (validUser(user, loginRequest)) {
            String token = jwtUtils.generateJwtToken(user.get());
            return new JwtResponse(token);
        }
        throw new IllegalArgumentException("User not found or not valid");
    }

    private boolean validUser(Optional<User> user, LoginRequest loginRequest) {
        return user.isPresent() && user.get().getState().equals(User.State.STATE_ACTIVE)
                && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword());
    }
}
