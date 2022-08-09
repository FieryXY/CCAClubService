package com.codingoutreach.clubservice.controllers.DO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PasswordCodeVerificationRequest {
    private UUID clubId;
    private String resetCode;
}
