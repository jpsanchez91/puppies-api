package com.puppies.infrastructure.persistence.Post.mapper;

import com.puppies.domain.Post.entity.Post;
import com.puppies.presentation.rest.Post.dto.CreatePostDTO;
import com.puppies.presentation.rest.Post.dto.CreatePostResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CreatePostMapper {

    Post toPost(CreatePostDTO createPostDTO);

    @Mapping(target = "userId", source = "user.id")
    CreatePostResponseDTO toPostResponseDTO(Post post);

    default String map(UUID value) {
        return Optional.ofNullable(value).map(UUID::toString).orElse(null);
    }
}
