package com.codingoutreach.clubservice.repository.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FeaturedClubInformation {
    @NotNull
    private final UUID clubId;

    @NotBlank
    private final String description;

    @NotBlank
    private final String mediaURL;
}
