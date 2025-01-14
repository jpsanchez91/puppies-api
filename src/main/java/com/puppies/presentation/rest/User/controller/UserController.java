package com.puppies.presentation.rest.Post.controller;

import com.puppies.application.User.service.UserService;
import com.puppies.infrastructure.Auth.service.AuthService;
import com.puppies.presentation.rest.Post.dto.AuthenticateUserDTO;
import com.puppies.presentation.rest.Post.dto.AuthenticationResponseDTO;
import com.puppies.presentation.rest.Post.dto.CreateUserDTO;
import com.puppies.presentation.rest.User.dto.UserDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created user"),
            @ApiResponse(responseCode = "400", description = "Invalid User object"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserDTO userDTO) {
        var user = userService.createUser(userDTO);
        return new ResponseEntity<>(user.getId().toString(), HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}/profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returns user prfile"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public UserDto fetchUserProfile(@PathVariable String uuid) {
        return userService.fetchUserProfile(uuid);
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated user"),
            @ApiResponse(responseCode = "400", description = "Invalid User object"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthenticationResponseDTO> authenticateUser(@RequestBody @Valid AuthenticateUserDTO userLoginDTO) throws BadRequestException {
        AuthenticationResponseDTO authResponse = authService.authenticateUser(userLoginDTO);
        return ResponseEntity.ok(authResponse);
    }
}