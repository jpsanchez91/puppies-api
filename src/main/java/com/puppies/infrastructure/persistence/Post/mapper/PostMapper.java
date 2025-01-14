package com.puppies.infrastructure.persistence.Post.mapper;

import com.puppies.domain.Post.entity.Post;
import com.puppies.presentation.rest.Post.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "userId", source = "user.id")
    PostDTO toPostDTO(Post post);

    Post toPost(PostDTO postDTO);

    default String map(UUID value) {
        return Optional.ofNullable(value).map(UUID::toString).orElse(null);
    }
}
