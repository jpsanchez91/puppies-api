package com.puppies.presentation.rest.User.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puppies.presentation.rest.Post.dto.AuthenticateUserDTO;
import com.puppies.presentation.rest.Post.dto.CreateUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_shouldReturn201() throws Exception {
        String newUser = "{ \"email\": \"dave@example.com\", \"password\": \"password\", \"name\": \"Dave\" }";

        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(newUser))
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
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setName("Bob");
        createUserDTO.setEmail("bob@example.com");
        createUserDTO.setPassword("password");

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isCreated());

        AuthenticateUserDTO authenticateUserDTO = new AuthenticateUserDTO();
        authenticateUserDTO.setEmail("bob@example.com");
        authenticateUserDTO.setPassword("password");

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

