package com.puppies.infrastructure.persistence.User.mapper;

import com.puppies.domain.User.entity.User;
import com.puppies.presentation.rest.User.dto.CreateUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateUserMapper {

    CreateUserDTO toUserDTO(User user);

    User toUser(CreateUserDTO userDTO);
}
