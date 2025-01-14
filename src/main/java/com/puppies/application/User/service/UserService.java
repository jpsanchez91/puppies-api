package com.puppies.application.User.service;

import com.puppies.domain.User.entity.User;
import com.puppies.presentation.rest.Post.dto.CreateUserDTO;

import java.util.Optional;

public interface UserService {
    User createUser(CreateUserDTO createUser);
    Optional<User> findById (String id);
  }
