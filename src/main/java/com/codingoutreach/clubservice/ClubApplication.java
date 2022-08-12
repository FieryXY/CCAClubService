package com.codingoutreach.clubservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class ClubApplication {

    public static final String WEBSITE_BASE_URL = "http://localhost:3000";
    public static final long SECONDS_UNTIL_PASSWORD_REQUEST_EXPIRATION = 60 * 60 * 24;
    public static final String WEBSITE_RESET_PASSWORD_URL = WEBSITE_BASE_URL + "/#/reset-password/";

    public static void main(String[] args) {
        SpringApplication.run(ClubApplication.class, args);
    }

    @GetMapping
    public List<String> hello() {
        return List.of("Hello", "World");
    }
}
