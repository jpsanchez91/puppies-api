package com.puppies.application.User.service.impl;

import com.puppies.application.User.service.UserService;
import com.puppies.domain.User.entity.User;
import com.puppies.infrastructure.exception.ConflictException;
import com.puppies.infrastructure.persistence.User.mapper.CreateUserMapper;
import com.puppies.infrastructure.persistence.User.repository.UserRepository;
import com.puppies.presentation.rest.Post.dto.CreateUserDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final CreateUserMapper createUserMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(CreateUserMapper createUserMapper, UserRepository userRepository) {
        this.createUserMapper = createUserMapper;
        this.userRepository = userRepository;
    }
    @Override
    public User createUser(CreateUserDTO createUser) {
        userRepository.findByEmail(createUser.getEmail())
                .ifPresent(existingUser -> { throw new ConflictException("User with this email already exists."); });

        User user = createUserMapper.toUser(createUser);
        user.setPassword(createUser.getPassword());

        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(UUID.fromString(id));
    }

}
