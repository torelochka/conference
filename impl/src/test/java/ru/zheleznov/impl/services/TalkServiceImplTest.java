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
import ru.zheleznov.impl.models.Schedule;
import ru.zheleznov.impl.models.Talk;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.TalkRepository;
import ru.zheleznov.impl.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("TalkServiceImpl is working then")
@AutoConfigureMockMvc
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class TalkServiceImplTest {

    @Autowired
    @MockBean
    private TalkRepository talkRepository;

    @Autowired
    @InjectMocks
    private TalkServiceImpl talkService;

    @Nested
    @DisplayName("getTalk() is working when")
    class getTalk {

        @Test
        void get_talk_exist_id() {
            Long existId = 2L;

            Talk talk = Talk.builder()
                    .id(existId)
                    .build();

            when(talkRepository.findById(existId)).thenReturn(Optional.of(talk));

            Assert.notNull(talkService.getTalk(existId));
        }

        @Test
        void get_talk_not_exist_id_throw_exception() {
            Long notExistId = 1L;
            Long existId = 2L;

            Talk talk = Talk.builder()
                    .id(existId)
                    .build();

            when(talkRepository.findById(existId)).thenReturn(Optional.of(talk));

            assertThrows(EntityNotFoundException.class,
                    () -> talkService.getTalk(notExistId));
        }
    }
}
