package com.codingoutreach.clubservice.controllers.DO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PostCreationRequest {
    @NotNull
    private final UUID club_id;

    @NotBlank
    private String title;

    @NotBlank
    private String text_content;

    @NotBlank
    private String media_url;
}
