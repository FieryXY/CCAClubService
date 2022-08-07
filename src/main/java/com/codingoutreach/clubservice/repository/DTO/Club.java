package com.codingoutreach.clubservice.repository.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;


/**
 * Database representation of row from "club" table
 */
 
// Makes getters and setters
@Data

// Creates constructors
@AllArgsConstructor
public class Club {
    @NotNull
    private final UUID clubID;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String encodedPassword;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String meetTime;

    @NotBlank
    private String profilePictureUrl;


}
