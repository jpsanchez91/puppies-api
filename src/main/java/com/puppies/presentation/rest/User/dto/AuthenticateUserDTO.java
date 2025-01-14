package com.puppies.presentation.rest.Post.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticateUserDTO {
    @Email(message = "Invalid Email")
    @NotBlank(message = "Invalid Email: Empty Email")
    private String email;
    @NotBlank(message = "Invalid Password: Empty Password")
    private String password;
}
