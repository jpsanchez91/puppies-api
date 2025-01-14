package com.puppies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class TestPuppiesApplication {

    public static void main(String[] args) {
        SpringApplication.from(PuppiesApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
