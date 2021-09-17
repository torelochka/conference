package ru.zheleznov.impl.services;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("UserServiceImpl is working then")
@AutoConfigureMockMvc
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class UserServiceImplTest {

    @Autowired
    @MockBean
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    @DisplayName("confirmEmail() is working when")
    class getUser {

        @Test
        void confirm_email_by_exist_code() {
            UUID existCode = UUID.randomUUID();

            User user = User.builder()
                    .id(0L)
                    .build();

            when(userRepository.findByConfirmedCode(existCode)).thenReturn(Optional.of(user));

            userService.confirmEmail(existCode);
        }

        @Test
        void confirm_email_by_not_exist_code_throw_exception() {
            UUID notExistCode = UUID.randomUUID();

            when(userRepository.findByConfirmedCode(notExistCode)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class,
                    () -> userService.confirmEmail(notExistCode));
        }
    }
}
