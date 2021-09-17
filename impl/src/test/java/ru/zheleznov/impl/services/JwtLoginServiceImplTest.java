package ru.zheleznov.impl.services;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.zheleznov.api.dto.JwtResponse;
import ru.zheleznov.api.forms.LoginRequest;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("JwtLoginServiceImpl is working then")
@AutoConfigureMockMvc
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class JwtLoginServiceImplTest {

    @Autowired
    @MockBean
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private JwtLoginServiceImpl jwtLoginService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Nested
    @DisplayName("signIn() is working when")
    class getUser {

        @Test
        void signIn_valid_user() {
            String emailConfirmed = "testEmail";

            User user = User.builder()
                    .email(emailConfirmed)
                    .id(0L)
                    .password(passwordEncoder.encode("test"))
                    .state(User.State.STATE_ACTIVE)
                    .build();

            when(userRepository.findByEmail(emailConfirmed)).thenReturn(Optional.of(user));

            LoginRequest loginRequest = LoginRequest.builder()
                    .email(emailConfirmed)
                    .password("test")
                    .build();

            JwtResponse jwtResponse = jwtLoginService.signIn(loginRequest);
            Assert.notNull(jwtResponse);
        }

        @Test
        void not_signIn_without_email_confirmed_valid_user() {
            String emailNotConfirmed = "testEmail";

            User user = User.builder()
                    .email(emailNotConfirmed)
                    .id(0L)
                    .password(passwordEncoder.encode("test"))
                    .build();

            when(userRepository.findByEmail(emailNotConfirmed)).thenReturn(Optional.of(user));

            LoginRequest loginRequest = LoginRequest.builder()
                    .email(emailNotConfirmed)
                    .password("test")
                    .build();


            assertThrows(IllegalArgumentException.class,
                    () -> jwtLoginService.signIn(loginRequest));
        }
    }
}
