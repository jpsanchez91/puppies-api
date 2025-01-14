package com.puppies.presentation.rest.Post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PostDTO {
    private String content;
    private UUID id;
    private String image;
    private String userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm:ss")
    private LocalDateTime date;
}
