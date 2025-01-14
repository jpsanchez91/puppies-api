package com.puppies.presentation.rest.User.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puppies.TestcontainersConfiguration;
import com.puppies.presentation.rest.Post.controller.UserController;
import com.puppies.presentation.rest.Post.dto.AuthenticateUserDTO;
import com.puppies.presentation.rest.Post.dto.CreateUserDTO;
import com.puppies.presentation.rest.User.controller.fixture.UserFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_shouldReturn201() throws Exception {
        CreateUserDTO createUserDTO = UserFixture.createRandomUser();

        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void fetchUserProfile_shouldReturn404() throws Exception {
        String invalidUuid = "00000000-0000-0000-0000-000000000000";

        mockMvc.perform(get("/user/{uuid}/profile", invalidUuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void authenticateUser_shouldReturn200() throws Exception {
        CreateUserDTO createUserDTO = UserFixture.createRandomUser();
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isCreated());

        AuthenticateUserDTO authenticateUserDTO = new AuthenticateUserDTO();
        authenticateUserDTO.setEmail(createUserDTO.getEmail());
        authenticateUserDTO.setPassword(createUserDTO.getPassword());

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticateUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").isNotEmpty());
    }

    @Test
    void authenticateUser_shouldReturn400() throws Exception {
        AuthenticateUserDTO invalidUser = new AuthenticateUserDTO();
        invalidUser.setEmail("invalid@example.com");
        invalidUser.setPassword("wrongpassword");

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }
}

