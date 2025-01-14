package com.puppies.presentation.rest.Post.controller.fixture;

import com.github.javafaker.Faker;
import com.puppies.presentation.rest.Post.dto.CreatePostDTO;

public class PostFixture {

    private static final Faker faker = new Faker();


    public static CreatePostDTO createRandomPost() {
        CreatePostDTO postDTO = new CreatePostDTO();

        postDTO.setContent(faker.lorem().paragraph());
        postDTO.setImage("base64:" + faker.internet().uuid());

        return postDTO;
    }
}
