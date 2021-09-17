package ru.zheleznov.web.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.zheleznov.impl.models.Room;
import ru.zheleznov.impl.repositories.RoomRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("RoomController is working then")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomRepository mockedRoomRepository;

    @Nested
    @DisplayName("getRooms() is working when")
    class getRooms {

        @Test
        public void get_list_rooms() throws Exception {

            List<Room> rooms = Collections.singletonList(Room.builder()
                    .id(0L)
                    .title("test")
                    .build());

            when(mockedRoomRepository.findAll()).thenReturn(rooms);

            mockMvc.perform(get("/rooms"))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

    }

}
