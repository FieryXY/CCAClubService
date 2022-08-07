package com.codingoutreach.clubservice.repository.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data

@AllArgsConstructor
public class Post {
    @NotNull
    private final UUID post_id;

    @NotNull
    private final UUID sender;

    @NotBlank
    private String title;

    @NotBlank
    private String text_content;

    @NotBlank
    private String media_url;
}
