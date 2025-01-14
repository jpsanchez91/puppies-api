package com.puppies.presentation.rest.User.controller.fixture;

import com.github.javafaker.Faker;
import com.puppies.presentation.rest.Post.dto.CreateUserDTO;

public class UserFixture {

    private static final Faker faker = new Faker();

    public static CreateUserDTO createRandomUser() {
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setName(faker.name().firstName());
        userDTO.setEmail(faker.internet().emailAddress());
        userDTO.setPassword(faker.internet().password());
        return userDTO;
    }
}
