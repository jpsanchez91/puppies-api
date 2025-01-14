package com.puppies.infrastructure.persistence.User.mapper;

import com.puppies.domain.User.entity.User;
import com.puppies.presentation.rest.Post.dto.CreateUserDTO;
import com.puppies.presentation.rest.User.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateUserMapper {

    UserDto toUserDto(User user);

    User toUser(CreateUserDTO userDTO);
}
