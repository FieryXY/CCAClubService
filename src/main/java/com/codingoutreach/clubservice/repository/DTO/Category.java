package com.codingoutreach.clubservice.repository.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Database representation of row from "category" table
 */
@Data
@AllArgsConstructor
public class Category {
    @NotNull
    UUID categoryId;

    @NotBlank
    String categoryName;
}
