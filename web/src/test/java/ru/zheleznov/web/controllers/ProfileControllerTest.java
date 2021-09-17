package ru.zheleznov.web.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.zheleznov.api.services.UserService;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.UserRepository;
import ru.zheleznov.impl.utils.JwtUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProfileController is working then")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class ProfileControllerTest {

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
    }

    @Nested
    @DisplayName("getUser() is working when")
    class getUser {

        @Test
        public void get_user_valid_token() throws Exception {
            mockMvc.perform(get("/getUser?access_token=" + adminToken))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        public void get_user_not_valid_token() throws Exception {

            adminToken += "2";

            mockMvc.perform(get("/getUser?access_token=" + adminToken))
                    .andExpect(status().is(409))
                    .andDo(print());
        }

    }

}
