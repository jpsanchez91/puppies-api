package com.puppies.application.Post.service.impl;

import com.puppies.application.Post.service.PostService;
import com.puppies.application.User.service.UserService;
import com.puppies.domain.Post.entity.Post;
import com.puppies.infrastructure.persistence.Post.mapper.CreatePostMapper;
import com.puppies.infrastructure.persistence.Post.mapper.PostMapper;
import com.puppies.infrastructure.persistence.Post.repository.PostRepository;
import com.puppies.presentation.rest.Post.dto.CreatePostDTO;
import com.puppies.presentation.rest.Post.dto.CreatePostResponseDTO;
import com.puppies.presentation.rest.Post.dto.PostDTO;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CreatePostMapper createPostMapper;
    private final UserService userService;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, CreatePostMapper createPostMapper, UserService userService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.createPostMapper = createPostMapper;
        this.userService = userService;
        this.postMapper = postMapper;
    }


    @Override
    public CreatePostResponseDTO createPost(CreatePostDTO createPostDTO, String userUUID) {
        var user = userService.findById(userUUID)
                .orElseThrow(() -> new NotFoundException("user not found"));
        Post post = createPostMapper.toPost(createPostDTO);
        post.setDate();
        post.setUser(user);
        return createPostMapper.toPostResponseDTO(postRepository.save(post));
    }

    @Override
    public PostDTO findById(String id) {
        return postMapper.toPostDTO(
                postRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new NotFoundException("Post not found")));
    }

    @Override
    public List<PostDTO> fetchUserFeed(String userUUID) {
        var user = userService.findById(userUUID)
                .orElseThrow(() -> new NotFoundException("user not found"));

        return postRepository.findByUserNotOrderByDateDesc(user).stream()
                .map(postMapper::toPostDTO).toList();
    }

    @Override
    public List<PostDTO> userPosts(String userUUID) {
        var user = userService.findById(userUUID)
                .orElseThrow(() -> new NotFoundException("user not found"));

        return postRepository.findByUserOrderByDateDesc(user).stream()
                .map(postMapper::toPostDTO).toList();
    }
}
