package ru.zheleznov.impl.services;

import io.jsonwebtoken.lang.Assert;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.zheleznov.api.dto.JwtResponse;
import ru.zheleznov.api.forms.LoginRequest;
import ru.zheleznov.api.forms.SignUpForm;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("SignUpServiceImpl is working then")
@AutoConfigureMockMvc
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class SignUpServiceImplTest {

    @Autowired
    @MockBean
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private SignUpServiceImpl signUpService;

    @Nested
    @DisplayName("signUp() is working when")
    class saveUser {

        @Test
        void signUp_user_with_not_exist_email() {
            String emailNotExist = "test@test.ze";

            SignUpForm signUpForm = SignUpForm.builder()
                    .email(emailNotExist)
                    .password("sss")
                    .build();

            User mockUser = User.builder()
                    .id(0L)
                    .build();

            when(userRepository.findByEmail(emailNotExist)).thenReturn(Optional.empty());
            when(userRepository.save(any())).thenReturn(mockUser);

            Assert.notNull(signUpService.signUp(signUpForm));
        }

        @Test
        void signUp_user_with_exist_email_throw_exception() {
            String emailExist = "test@test.ze";

            SignUpForm signUpForm = SignUpForm.builder()
                    .email(emailExist)
                    .password("sss")
                    .build();

            User mockUser = User.builder()
                    .id(0L)
                    .build();

            when(userRepository.findByEmail(emailExist)).thenReturn(Optional.of(mockUser));
            when(userRepository.save(any())).thenReturn(mockUser);

            assertThrows(IllegalArgumentException.class,
                    () -> signUpService.signUp(signUpForm));
        }
    }
}
