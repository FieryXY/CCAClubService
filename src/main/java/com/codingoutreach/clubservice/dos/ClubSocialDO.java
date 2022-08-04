package com.codingoutreach.clubservice.dos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Represents one social of a club
 */
@Data
@AllArgsConstructor
public class ClubSocialDO {

    @NotNull
    private UUID socialId;

    @NotBlank
    private String socialName;

    @NotBlank
    private String socialLink;
}
