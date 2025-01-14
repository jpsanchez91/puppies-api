package com.puppies.application.Post.service;

import com.puppies.domain.Post.entity.Post;
import com.puppies.presentation.rest.Post.dto.CreatePostDTO;
import com.puppies.presentation.rest.Post.dto.CreatePostResponseDTO;
import com.puppies.presentation.rest.Post.dto.PostDTO;

import java.util.List;

public interface PostService {
    CreatePostResponseDTO createPost(CreatePostDTO createPostDTO, String userUUID);
    PostDTO findById(String id);
    List<PostDTO> fetchUserFeed(String userUUID);
    List<PostDTO> userPosts(String userUUID);
}
