package com.codingoutreach.clubservice.dos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * All information needed to load the Club Profile Page
 */
@Data
@AllArgsConstructor
public class ClubInformation {
    @NotNull
    private final UUID clubId;

    @NotBlank
    private String clubName;

    private final String description;

    private final String profilePictureUrl;

    @NotNull
    private List<ClubSocialDO> clubSocialDOS;

    @NotNull
    private List<String> clubCategories;
}
