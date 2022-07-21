package com.codingoutreach.clubservice.dos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Represents one social of a club
 */
@Data
@AllArgsConstructor
public class ClubSocialDO {

    private String socialName;

    @NotBlank
    private String socialLink;
}
