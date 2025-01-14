package com.puppies.infrastructure.persistence.Post.mapper;

import com.puppies.domain.Post.entity.Post;
import com.puppies.presentation.rest.Post.dto.CreatePostDTO;
import com.puppies.presentation.rest.Post.dto.CreatePostResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatePostMapper {

    Post toPost(CreatePostDTO createPostDTO);

    CreatePostResponseDTO toPostResponseDTO(Post post);
}
