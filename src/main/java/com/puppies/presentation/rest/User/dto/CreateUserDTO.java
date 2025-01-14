package com.puppies.presentation.rest.Post.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserDTO {
    @NotBlank(message = "Invalid Name: Empty name")
    private String name;
    @NotBlank(message = "Invalid Email: Empty Email")
    @Email(message = "Invalid Email")
    private String email;
    @NotBlank(message = "Invalid Password: Empty Password")
    private String password;
}
