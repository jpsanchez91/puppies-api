package com.puppies.infrastructure.persistence.Post.mapper;

import com.puppies.domain.Post.entity.Post;
import com.puppies.presentation.rest.Post.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDTO toPostDTO(Post post);

    Post toPost(PostDTO postDTO);
}
