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
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.ScheduleRepository;
import ru.zheleznov.impl.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("ScheduleServiceImpl is working then")
@AutoConfigureMockMvc
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class ScheduleServiceImplTest {

    @Autowired
    @MockBean
    private ScheduleRepository scheduleRepository;

    @Autowired
    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Nested
    @DisplayName("getScheduleByTalkId() is working when")
    class getUser {

        @Test
        void get_schedule_exist_id() {
            Long existId = 2L;

            Schedule schedule = Schedule.builder()
                    .id(existId)
                    .build();

            when(scheduleRepository.findByTalkId(existId)).thenReturn(Optional.of(schedule));

            Assert.notNull(scheduleService.getScheduleByTalkId(existId));
        }

        @Test
        void get_schedule_not_exist_id_throw_exception() {
            Long notExistId = 1L;
            Long existId = 2L;

            Schedule schedule = Schedule.builder()
                    .id(existId)
                    .build();

            when(scheduleRepository.findByTalkId(existId)).thenReturn(Optional.of(schedule));

            assertThrows(EntityNotFoundException.class,
                    () -> scheduleService.getScheduleByTalkId(notExistId));
        }
    }
}
