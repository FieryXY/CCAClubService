package com.codingoutreach.clubservice.repository.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

// Makes getters and setters
@Data

// Creates constructors...?
@AllArgsConstructor

public class Club {
    @NotNull
    private final UUID clubID;

    @NotBlank
    private String email;

    @NotBlank
    private String encoded_password;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String meet_time;

    @NotBlank
    private String profile_picture_url;

    

}

