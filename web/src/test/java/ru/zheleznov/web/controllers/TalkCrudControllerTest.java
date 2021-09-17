package ru.zheleznov.web.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.zheleznov.api.services.TalkService;
import ru.zheleznov.impl.models.Talk;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.TalkRepository;
import ru.zheleznov.impl.utils.JwtUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("TalkCrudController is working then")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class TalkCrudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private TalkService mockedTalkService;

    @MockBean
    private TalkRepository mockedTalkRepository;

    private String speakerToken;
    private String listenerToken;

    @BeforeEach
    public void setUp() {
        User speakerUser = User.builder()
                .id(0L)
                .email("admin")
                .role(User.Role.SPEAKER)
                .build();

        speakerToken = jwtUtils.generateJwtToken(speakerUser);

        User listenerUser = User.builder()
                .id(0L)
                .email("listener")
                .role(User.Role.LISTENER)
                .build();

        listenerToken = jwtUtils.generateJwtToken(listenerUser);
    }

    @Nested
    @DisplayName("deleteTalk() is working when")
    class deleteTalk {

        @Test
        public void delete_talk_exist_id() throws Exception {
            Long existId = 0L;

            when(mockedTalkRepository.findById(existId)).thenReturn(Optional.of(new Talk()));
            when(mockedTalkService.deleteTalk(any(), any())).thenReturn(true);

            mockMvc.perform(delete("/talk/" + existId)
                    .header("Authorization", "Bearer " + speakerToken))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        public void delete_user_exist_id_not_speaker() throws Exception {
            Long existId = 0L;

            when(mockedTalkRepository.findById(existId)).thenReturn(Optional.of(new Talk()));
            when(mockedTalkService.deleteTalk(any(), any())).thenReturn(true);

            mockMvc.perform(delete("/talk/" + existId)
                    .header("Authorization", "Bearer " + listenerToken))
                    .andExpect(status().isForbidden())
                    .andDo(print());
        }

    }

}
