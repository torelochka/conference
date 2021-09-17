package ru.zheleznov.impl.services;

import com.sun.javaws.exceptions.InvalidArgumentException;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.zheleznov.api.dto.JwtResponse;
import ru.zheleznov.api.forms.LoginRequest;
import ru.zheleznov.impl.models.User;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("MailServiceImpl is working then")
@AutoConfigureMockMvc
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class MailServiceImplTest {

    @Autowired
    private MailServiceImpl mailService;

    @Nested
    @DisplayName("sendEmailForConfirm() is working when")
    class sendEmail {

        @Test
        void send_valid_mail_to_confirm() {
            mailService.sendEmailForConfirm("test@test.com", UUID.randomUUID().toString());
        }

        @Test
        void send_invalid_mail_to_confirm_throw_exception() {
            assertThrows(IllegalArgumentException.class,
                    () -> mailService.sendEmailForConfirm("not valid", UUID.randomUUID().toString()));
        }
    }
}
