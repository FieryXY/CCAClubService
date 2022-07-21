package com.codingoutreach.clubservice.controllers.DO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ClubCreationRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String meet_time;

}
