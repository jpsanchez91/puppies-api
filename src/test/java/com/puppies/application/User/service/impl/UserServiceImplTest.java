package com.puppies.application.User.service.impl;

import com.puppies.domain.User.entity.User;
import com.puppies.infrastructure.exception.ConflictException;
import com.puppies.infrastructure.persistence.User.mapper.CreateUserMapper;
import com.puppies.infrastructure.persistence.User.repository.UserRepository;
import com.puppies.presentation.rest.Post.dto.CreateUserDTO;
import com.puppies.presentation.rest.User.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.webjars.NotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    CreateUserMapper createUserMapper;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("test");
        createUserDTO.setPassword("test");
        createUserDTO.setEmail("test@test.com");
        User mockUser = new User();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(createUserMapper.toUser(createUserDTO)).thenReturn(mockUser);
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        User result = userServiceImpl.createUser(createUserDTO);
        Assertions.assertEquals(mockUser, result);
    }

    @Test
    void testCreateUserConflictException() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("test@example.com");
        User existingUser = new User();

        when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(Optional.of(existingUser));

        Assertions.assertThrows(ConflictException.class, () ->
                userServiceImpl.createUser(createUserDTO)
        );
    }

    @Test
    void testFindById() {
        UUID userId = UUID.randomUUID();
        User mockUser = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        Optional<User> result = userServiceImpl.findById(userId.toString());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockUser, result.get());
    }

    @Test
    void testFindByIdNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userServiceImpl.findById(userId.toString());
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void testFetchUserProfile() {
        UUID userId = UUID.randomUUID();
        User mockUser = new User();
        UserDto mockUserDto = new UserDto();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(createUserMapper.toUserDto(mockUser)).thenReturn(mockUserDto);

        UserDto result = userServiceImpl.fetchUserProfile(userId.toString());
        Assertions.assertEquals(mockUserDto, result);
    }

    @Test
    void testFetchUserProfileNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->
                userServiceImpl.fetchUserProfile(userId.toString())
        );
    }
}
