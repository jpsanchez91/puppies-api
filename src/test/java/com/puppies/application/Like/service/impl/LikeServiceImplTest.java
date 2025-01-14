package com.puppies.application.Like.service.impl;

import com.puppies.application.User.service.UserService;
import com.puppies.domain.Like.entity.Like;
import com.puppies.domain.Post.entity.Post;
import com.puppies.domain.User.entity.User;
import com.puppies.infrastructure.persistence.Like.repository.LikeRepository;
import com.puppies.infrastructure.persistence.Post.repository.PostRepository;
import com.puppies.presentation.rest.Like.dto.CreateLikeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LikeServiceImplTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private LikeServiceImpl likeServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateLike() {
        CreateLikeDTO createLikeDTO = new CreateLikeDTO();
        createLikeDTO.setPostId(UUID.randomUUID().toString());

        String userId = UUID.randomUUID().toString();

        User user = new User();
        user.setId(UUID.randomUUID());

        Post post = new Post();
        post.setId(UUID.randomUUID());

        when(userService.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(UUID.fromString(createLikeDTO.getPostId()))).thenReturn(Optional.of(post));

        Like like = new Like();
        like.setId(UUID.randomUUID());
        like.setUser(user);
        like.setPost(post);

        when(likeRepository.save(any(Like.class))).thenReturn(like);

        UUID result = likeServiceImpl.createLike(createLikeDTO, userId);

        assertEquals(like.getId(), result);
    }
}