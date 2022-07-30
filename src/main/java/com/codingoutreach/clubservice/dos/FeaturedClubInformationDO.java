package com.codingoutreach.clubservice.dos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FeaturedClubInformationDO {
    @NotNull
    private final UUID clubId;

    @NotBlank
    private final String clubName;

    @NotBlank
    private final String description;

    @NotBlank
    private final String mediaURL;
}
