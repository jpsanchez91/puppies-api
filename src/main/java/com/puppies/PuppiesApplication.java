package com.puppies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class PuppiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PuppiesApplication.class, args);
    }

}
