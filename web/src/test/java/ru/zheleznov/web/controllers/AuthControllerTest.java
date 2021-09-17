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
import ru.zheleznov.api.services.UserService;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.UserRepository;
import ru.zheleznov.impl.utils.JwtUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AuthController is working then")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserRepository mockedUserRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("registerUser() is working when")
    class registerUser {

        @Test
        public void signUp_valid_user() throws Exception {

            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .email("test@test.com")
                    .password("Qwerty007")
                    .build();

            when(mockedUserRepository.save(any())).thenReturn(new User());
            when(mockedUserRepository.findByEmail(any())).thenReturn(Optional.empty());

            mockMvc.perform(post("/api/auth/signUp")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequest)))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        public void signUp_not_valid_user_throw_exception() throws Exception {

            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .email("test@test.com")
                    .password("qwey007")
                    .build();

            when(mockedUserRepository.save(any())).thenReturn(new User());
            when(mockedUserRepository.findByEmail(any())).thenReturn(Optional.empty());

            mockMvc.perform(post("/api/auth/signUp")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequest)))
                    .andExpect(status().is(400))
                    .andDo(print());
        }
    }

}
