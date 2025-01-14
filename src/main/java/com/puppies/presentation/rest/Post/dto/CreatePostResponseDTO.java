package com.puppies.presentation.rest.Post.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreatePostResponseDTO {
    private UUID id;

    private String image;

    private String content;

    private LocalDateTime date;

    private String userId;
}
