package ru.zheleznov.web.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.zheleznov.api.services.SignUpService;
import ru.zheleznov.api.services.UserService;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.UserRepository;
import ru.zheleznov.impl.utils.JwtUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AdminController is working then")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private UserService mockedUserService;

    @MockBean
    private UserRepository mockedUserRepository;

    private String adminToken;
    private String listenerToken;

    @BeforeEach
    public void setUp() {
        User adminUser = User.builder()
                .id(0L)
                .email("admin")
                .role(User.Role.ADMIN)
                .build();

        adminToken = jwtUtils.generateJwtToken(adminUser);

        User listenerUser = User.builder()
                .id(0L)
                .email("listener")
                .role(User.Role.LISTENER)
                .build();

        listenerToken = jwtUtils.generateJwtToken(listenerUser);
    }

    @Nested
    @DisplayName("userDelete() is working when")
    class deleteUser {

        @Test
        public void delete_user_exist_id() throws Exception {
            Long existId = 0L;

            User user = User.builder()
                    .id(existId)
                    .build();

            when(mockedUserRepository.findById(existId)).thenReturn(Optional.of(user));
            doNothing().when(mockedUserService).deleteUser(any());

            mockMvc.perform(delete("/user/" + existId)
                    .header("Authorization", "Bearer " + adminToken))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        public void delete_user_exist_id_not_admin() throws Exception {
            Long existId = 0L;

            User user = User.builder()
                    .id(existId)
                    .build();

            when(mockedUserRepository.findById(existId)).thenReturn(Optional.of(user));
            doNothing().when(mockedUserService).deleteUser(any());

            mockMvc.perform(delete("/user/" + existId)
                    .header("Authorization", "Bearer " + listenerToken))
                    .andExpect(status().isForbidden())
                    .andDo(print());
        }

    }

}
