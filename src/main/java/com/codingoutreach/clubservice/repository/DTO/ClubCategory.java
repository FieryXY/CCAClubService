package com.codingoutreach.clubservice.repository.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Database representation of row from "club_categories" table
 */
@Data
@AllArgsConstructor
public class ClubCategory {

    @NotNull
    UUID clubCategoryId;

    @NotNull
    UUID clubId;

    @NotNull
    UUID categoryId;

}
