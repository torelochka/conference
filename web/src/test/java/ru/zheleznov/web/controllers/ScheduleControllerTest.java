package ru.zheleznov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zheleznov.api.forms.SignUpRequest;
import ru.zheleznov.impl.models.Schedule;
import ru.zheleznov.impl.models.Talk;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.ScheduleRepository;
import ru.zheleznov.impl.repositories.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ScheduleController is working then")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ScheduleRepository mockedScheduleRepository;

    @Nested
    @DisplayName("scheduleByTalkId() is working when")
    class scheduleByTalkId {

        @Test
        public void get_schedule_by_exist_talk_id() throws Exception {
            Long existId = 0L;

            when(mockedScheduleRepository.findByTalkId(existId)).thenReturn(Optional.of(new Schedule()));

            mockMvc.perform(get("/schedule/" + existId))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        public void get_schedule_by_not_exist_talk_id() throws Exception {
            Long notExistId = 0L;

            when(mockedScheduleRepository.findByTalkId(notExistId)).thenReturn(Optional.empty());

            mockMvc.perform(get("/schedule/" + notExistId))
                    .andExpect(status().is(404))
                    .andDo(print());
        }
    }

}
