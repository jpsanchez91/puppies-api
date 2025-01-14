package com.puppies.presentation.rest.Post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatePostDTO {

    @NotBlank(message = "Image URL cannot be blank")
    private String image;

    @NotBlank(message = "Content cannot be blank")
    private String content;


}
