package com.puppies;

import org.springframework.boot.SpringApplication;

public class TestPuppiesApplication {

    public static void main(String[] args) {
        SpringApplication.from(PuppiesApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
