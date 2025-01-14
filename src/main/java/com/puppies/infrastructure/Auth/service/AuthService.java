package com.puppies.infrastructure.Auth.service;

import com.puppies.presentation.rest.Post.dto.AuthenticateUserDTO;
import com.puppies.presentation.rest.Post.dto.AuthenticationResponseDTO;
import org.apache.coyote.BadRequestException;

public interface AuthService {

    AuthenticationResponseDTO authenticateUser(AuthenticateUserDTO authenticateUserDTO) throws BadRequestException;
}
