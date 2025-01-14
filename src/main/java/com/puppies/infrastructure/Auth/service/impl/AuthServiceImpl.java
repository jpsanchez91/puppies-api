package com.puppies.infrastructure.Auth.service.impl;

import com.puppies.domain.User.entity.User;
import com.puppies.infrastructure.Auth.service.AuthService;
import com.puppies.infrastructure.persistence.User.repository.UserRepository;
import com.puppies.infrastructure.utils.JwtUtil;
import com.puppies.presentation.rest.Post.dto.AuthenticateUserDTO;
import com.puppies.presentation.rest.Post.dto.AuthenticationResponseDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl  implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl (UserRepository userRepository, AuthenticationProvider authenticationProvider, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthenticationResponseDTO authenticateUser(AuthenticateUserDTO authenticateUserDTO) throws BadRequestException {
        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticateUserDTO.getEmail(), authenticateUserDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadRequestException("user or password invalid");
        }

        final User user = userRepository.findByEmail(authenticateUserDTO.getEmail()).get();
        final String jwt = jwtUtil.generateToken(user);
        final String refreshToken = jwtUtil.generateRefreshToken(authenticateUserDTO.getEmail());

        return AuthenticationResponseDTO.builder().jwt(jwt).refreshToken(refreshToken).build();

    }
}
