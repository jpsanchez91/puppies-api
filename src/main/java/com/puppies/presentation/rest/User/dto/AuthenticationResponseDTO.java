package com.puppies.presentation.rest.Post.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponseDTO {
    private final String jwt;
    private final String refreshToken;
}
