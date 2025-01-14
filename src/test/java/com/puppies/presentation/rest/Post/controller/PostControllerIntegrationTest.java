package com.puppies.presentation.rest.Post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puppies.presentation.rest.Like.dto.CreateLikeDTO;
import com.puppies.presentation.rest.Post.dto.CreatePostDTO;
import com.puppies.presentation.rest.Post.dto.CreateUserDTO;
import com.puppies.presentation.rest.Post.dto.AuthenticateUserDTO;
import org.junit.jupiter.api.BeforeEach;
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
public class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    private String userId;

    @BeforeEach
    void setUp() throws Exception {
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setName("Alice");
        userDTO.setEmail("alice@example.com");
        userDTO.setPassword("password");

        userId = mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthenticateUserDTO authDTO = new AuthenticateUserDTO();
        authDTO.setEmail("alice@example.com");
        authDTO.setPassword("password");

        String response = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        jwtToken = objectMapper.readTree(response).get("jwt").asText();
    }

    @Test
    void createPost_shouldReturn201() throws Exception {
        CreatePostDTO postDTO = new CreatePostDTO();
        postDTO.setContent("This is a test post");
        postDTO.setImage("base64:dsdsdsdasdsasd");

        mockMvc.perform(post("/post/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void findById_shouldReturn200() throws Exception {
        CreatePostDTO postDTO = new CreatePostDTO();
        postDTO.setContent("This is another test post");
        postDTO.setImage("base64:dsdsdsdasdsasd");

        String postResponse = mockMvc.perform(post("/post/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String postId = objectMapper.readTree(postResponse).get("id").asText();

        mockMvc.perform(get("/post/{id}", postId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("This is another test post"));
    }

    @Test
    void fetchUserFeed_shouldReturn200() throws Exception {

        mockMvc.perform(get("/post/feed")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[*].userId").value(org.hamcrest.Matchers.not(userId)));
    }

    @Test
    void userPosts_shouldReturn200() throws Exception {
        CreatePostDTO postDTO = new CreatePostDTO();
        postDTO.setContent("This is a test post");
        postDTO.setImage("base64:dsdsdsdasdsasd");

        mockMvc.perform(post("/post/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/post/my")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[*].userId").value(org.hamcrest.Matchers.everyItem(org.hamcrest.Matchers.is(userId))));
    }

    @Test
    void fetchUserLikedPost_shouldReturn200() throws Exception {
        CreatePostDTO postDTO = new CreatePostDTO();
        postDTO.setContent("This is a test post");
        postDTO.setImage("base64:dsdsdsdasdsasd");

        var result = mockMvc.perform(post("/post/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var postId = objectMapper.readTree(result).get("id").asText();

        var createLike =  new CreateLikeDTO();
        createLike.setPostId(postId);

        mockMvc.perform(post("/like/post")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createLike)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/post/user/liked")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[*].userId").value(org.hamcrest.Matchers.everyItem(org.hamcrest.Matchers.is(userId))));
    }
}
