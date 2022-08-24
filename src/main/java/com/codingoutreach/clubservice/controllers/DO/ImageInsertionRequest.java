package com.codingoutreach.clubservice.controllers.DO;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ImageInsertionRequest {
    @NotBlank
    private String base64;
}
