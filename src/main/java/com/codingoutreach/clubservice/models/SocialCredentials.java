package com.codingoutreach.clubservice.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SocialCredentials {
    private String socialName;
    private String socialLink;
    private UUID socialId;
}
