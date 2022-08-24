package com.codingoutreach.clubservice.controllers.DO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocialCreationRequest {
    private String socialName;
    private String socialLink;
}
