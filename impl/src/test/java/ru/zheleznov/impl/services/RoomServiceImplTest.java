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
import ru.zheleznov.api.services.RoomService;
import ru.zheleznov.impl.models.Room;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.RoomRepository;
import ru.zheleznov.impl.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("RoomServiceImpl is working then")
@AutoConfigureMockMvc
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class RoomServiceImplTest {

    @Autowired
    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    @InjectMocks
    private RoomServiceImpl roomService;

    @Nested
    @DisplayName("getAllRooms() is working when")
    class getUser {

        @Test
        void get_list_with_rooms() {

            List<Room> rooms = Collections.singletonList(Room.builder()
                    .title("test room")
                    .id(2L)
                    .build());

            when(roomRepository.findAll()).thenReturn(rooms);

            Assert.notEmpty(roomService.getAllRooms());
        }
    }
}
