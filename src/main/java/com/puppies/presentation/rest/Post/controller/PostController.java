package com.puppies.presentation.rest.Post.controller;

import com.puppies.application.Post.service.PostService;
import com.puppies.presentation.rest.Post.dto.CreatePostDTO;
import com.puppies.presentation.rest.Post.dto.CreatePostResponseDTO;
import com.puppies.presentation.rest.Post.dto.PostDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post")
@PreAuthorize("isAuthenticated()")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created post"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The user is not found")
    })
    public ResponseEntity<CreatePostResponseDTO> createPost(@RequestBody @Valid CreatePostDTO dto, @AuthenticationPrincipal String uuid) {
        return new ResponseEntity<>(postService.createPost(dto, uuid), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found post"),
            @ApiResponse(responseCode = "404", description = "The post is not found")
    })
    public PostDTO findById(@PathVariable String id) {
        return postService.findById(id);
    }

    @GetMapping("/feed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched user feed"),
            @ApiResponse(responseCode = "404", description = "User feed not found")
    })
    public List<PostDTO> fetchUserFeed(@AuthenticationPrincipal String uuid) {
        return postService.fetchUserFeed(uuid);
    }

    @GetMapping("/my")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched user posts"),
            @ApiResponse(responseCode = "404", description = "User posts not found")
    })
    public List<PostDTO> userPosts(@AuthenticationPrincipal String uuid) {
        return postService.userPosts(uuid);
    }

    @GetMapping("/user/liked")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched user liked posts"),
            @ApiResponse(responseCode = "404", description = "User liked posts not found")
    })
    public List<PostDTO> fetchUserLikedPost(@AuthenticationPrincipal String uuid) {
        return postService.fetchUserLikedPost(uuid);
    }
}