package com.puppies.application.Like.service.impl;

import com.puppies.application.Like.service.LikeService;
import com.puppies.application.Post.service.PostService;
import com.puppies.application.User.service.UserService;
import com.puppies.domain.Like.entity.Like;
import com.puppies.infrastructure.exception.ConflictException;
import com.puppies.infrastructure.persistence.Like.repository.LikeRepository;
import com.puppies.infrastructure.persistence.Post.repository.PostRepository;
import com.puppies.presentation.rest.Like.dto.CreateLikeDTO;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostRepository postRepository;

    public LikeServiceImpl(LikeRepository likeRepository, UserService userService, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @Override
    public UUID createLike(CreateLikeDTO createLikeDTO, String userId) {
        var user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        var post = postRepository.findById(UUID.fromString(createLikeDTO.getPostId()))
                .orElseThrow(() -> new NotFoundException("Post not found"));

        likeRepository.findByUserAndPost(user, post)
                .ifPresent(existingLike -> {
                    throw new ConflictException("Post already liked");
                });


        var like = Like.builder().post(post).user(user).build();

        return likeRepository.save(like).getId();

    }
}
