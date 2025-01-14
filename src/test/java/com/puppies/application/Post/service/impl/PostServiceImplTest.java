package com.puppies.application.Post.service.impl;

import com.puppies.application.User.service.UserService;
import com.puppies.domain.Like.entity.Like;
import com.puppies.domain.Post.entity.Post;
import com.puppies.domain.User.entity.User;
import com.puppies.infrastructure.persistence.Like.repository.LikeRepository;
import com.puppies.infrastructure.persistence.Post.mapper.CreatePostMapper;
import com.puppies.infrastructure.persistence.Post.mapper.PostMapper;
import com.puppies.infrastructure.persistence.Post.repository.PostRepository;
import com.puppies.presentation.rest.Post.dto.CreatePostDTO;
import com.puppies.presentation.rest.Post.dto.CreatePostResponseDTO;
import com.puppies.presentation.rest.Post.dto.PostDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class PostServiceImplTest {
    @Mock
    PostRepository postRepository;
    @Mock
    CreatePostMapper createPostMapper;
    @Mock
    UserService userService;
    @Mock
    PostMapper postMapper;
    @Mock
    LikeRepository likeRepository;
    @InjectMocks
    PostServiceImpl postServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePost() {
        User mockUser = new User();
        Post mockPost = new Post();
        CreatePostDTO createPostDTO = new CreatePostDTO();
        CreatePostResponseDTO createPostResponseDTO = new CreatePostResponseDTO();

        when(userService.findById(anyString())).thenReturn(Optional.of(mockUser));
        when(createPostMapper.toPost(createPostDTO)).thenReturn(mockPost);
        when(postRepository.save(mockPost)).thenReturn(mockPost);
        when(createPostMapper.toPostResponseDTO(mockPost)).thenReturn(createPostResponseDTO);

        CreatePostResponseDTO result = postServiceImpl.createPost(createPostDTO, "userUUID");
        Assertions.assertEquals(createPostResponseDTO, result);
    }

    @Test
    void testCreatePostUserNotFound() {
        when(userService.findById(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->
                postServiceImpl.createPost(new CreatePostDTO(), "userUUID")
        );
    }

    @Test
    void testFindById() {
        Post mockPost = new Post();
        PostDTO mockPostDTO = new PostDTO();

        when(postRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockPost));
        when(postMapper.toPostDTO(mockPost)).thenReturn(mockPostDTO);

        PostDTO result = postServiceImpl.findById("397ad4c0-75bd-4d55-b2b8-b18ab82974e1");
        Assertions.assertEquals(mockPostDTO, result);
    }

    @Test
    void testFindByIdNotFound() {
        when(postRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->
                postServiceImpl.findById("397ad4c0-75bd-4d55-b2b8-b18ab82974e1")
        );
    }

    @Test
    void testFetchUserFeed() {
        User mockUser = new User();
        Post mockPost = new Post();
        PostDTO mockPostDTO = new PostDTO();

        when(userService.findById(anyString())).thenReturn(Optional.of(mockUser));
        when(postRepository.findByUserNotOrderByDateDesc(mockUser)).thenReturn(List.of(mockPost));
        when(postMapper.toPostDTO(mockPost)).thenReturn(mockPostDTO);

        List<PostDTO> result = postServiceImpl.fetchUserFeed("userUUID");
        Assertions.assertEquals(List.of(mockPostDTO), result);
    }

    @Test
    void testFetchUserFeedUserNotFound() {
        when(userService.findById(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->
                postServiceImpl.fetchUserFeed("userUUID")
        );
    }

    @Test
    void testUserPosts() {
        User mockUser = new User();
        Post mockPost = new Post();
        PostDTO mockPostDTO = new PostDTO();

        when(userService.findById(anyString())).thenReturn(Optional.of(mockUser));
        when(postRepository.findByUserOrderByDateDesc(mockUser)).thenReturn(List.of(mockPost));
        when(postMapper.toPostDTO(mockPost)).thenReturn(mockPostDTO);

        List<PostDTO> result = postServiceImpl.userPosts("userUUID");
        Assertions.assertEquals(List.of(mockPostDTO), result);
    }

    @Test
    void testUserPostsUserNotFound() {
        when(userService.findById(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->
                postServiceImpl.userPosts("userUUID")
        );
    }

    @Test
    void testFetchUserLikedPost() {
        User mockUser = new User();
        Post mockPost = new Post();
        Like mockLike = new Like(UUID.randomUUID(), mockUser, mockPost);
        PostDTO mockPostDTO = new PostDTO();

        when(userService.findById(anyString())).thenReturn(Optional.of(mockUser));
        when(likeRepository.findByUser(mockUser)).thenReturn(List.of(mockLike));
        when(postMapper.toPostDTO(mockPost)).thenReturn(mockPostDTO);

        List<PostDTO> result = postServiceImpl.fetchUserLikedPost("userUUID");
        Assertions.assertEquals(List.of(mockPostDTO), result);
    }

    @Test
    void testFetchUserLikedPostUserNotFound() {
        when(userService.findById(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->
                postServiceImpl.fetchUserLikedPost("userUUID")
        );
    }
}
