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
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("EmailConfirmationController is working then")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class EmailConfirmationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserRepository mockedUserRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("confirmEmail() is working when")
    class confirmEmail {

        @Test
        public void confirmed_exist_code() throws Exception {
            UUID code = UUID.randomUUID();

            when(mockedUserRepository.findByConfirmedCode(any())).thenReturn(Optional.of(new User()));

            mockMvc.perform(get("/confirmed/" + code))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        public void confirmed_not_exist_code() throws Exception {
            UUID code = UUID.randomUUID();

            when(mockedUserRepository.findByConfirmedCode(any())).thenReturn(Optional.empty());

            mockMvc.perform(get("/confirmed/" + code))
                    .andExpect(status().is(404))
                    .andDo(print());
        }
    }

}
