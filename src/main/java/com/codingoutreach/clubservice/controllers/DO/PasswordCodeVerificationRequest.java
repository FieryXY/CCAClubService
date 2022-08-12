package com.codingoutreach.clubservice.controllers.DO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PasswordCodeVerificationRequest {
    private String resetCode;
}
