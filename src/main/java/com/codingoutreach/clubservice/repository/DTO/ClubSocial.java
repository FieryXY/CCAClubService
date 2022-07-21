package com.codingoutreach.clubservice.repository.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Database representation of row from "socials" table
 */
@Data
@AllArgsConstructor
public class ClubSocial {

    @NotNull
    UUID clubSocialId;

    @NotNull
    UUID clubId;

    private String socialName;

    @NotBlank
    private String socialLink;
}
