package com.puppies.application.Like.service;

import com.puppies.presentation.rest.Like.dto.CreateLikeDTO;

import java.util.UUID;

public interface LikeService {
    UUID createLike(CreateLikeDTO createLikeDTO, String userId);
}
