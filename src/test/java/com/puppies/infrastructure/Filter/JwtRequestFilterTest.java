package com.puppies.infrastructure.Filter;

import com.puppies.infrastructure.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.webjars.NotFoundException;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JwtRequestFilterTest {
    @Mock
    JwtUtil jwtUtil;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;
    @InjectMocks
    JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternalValidToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(jwtUtil.extractUser("validToken")).thenReturn("userUUID");

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalInvalidToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        Assertions.assertThrows(BadCredentialsException.class, () ->
                jwtRequestFilter.doFilterInternal(request, response, filterChain)
        );

        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalNoToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        Assertions.assertThrows(BadCredentialsException.class, () -> jwtRequestFilter.doFilterInternal(request, response, filterChain));

    }
}
