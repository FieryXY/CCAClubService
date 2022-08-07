package com.codingoutreach.clubservice.repository.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data

@AllArgsConstructor
public class PostTab {

    @NotNull
    private final UUID tab_id;

    @NotNull
    private final UUID post_id;

    @NotBlank
    private String header;

    @NotBlank
    private String text_content;

    @NotBlank
    private String media_url;
}
