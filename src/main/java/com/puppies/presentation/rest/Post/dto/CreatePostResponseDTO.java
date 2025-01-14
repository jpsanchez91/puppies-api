package com.puppies.presentation.rest.Post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreatePostResponseDTO {
    private UUID id;

    private String image;

    private String content;

    @JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss")
    private LocalDateTime date;

    private String userId;
}
