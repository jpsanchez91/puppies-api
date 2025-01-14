package com.puppies.presentation.rest.Like.controller;

import com.puppies.application.Like.service.LikeService;
import com.puppies.presentation.rest.Like.dto.CreateLikeDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/like")
@PreAuthorize("isAuthenticated()")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully liked post"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The post or user is not found")
    })
    public ResponseEntity<UUID> createLike(@RequestBody CreateLikeDTO createLikeDTO, @AuthenticationPrincipal String userId) {
        return new ResponseEntity<>(likeService.createLike(createLikeDTO, userId), HttpStatus.CREATED);
    }
}
