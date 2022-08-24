package com.codingoutreach.clubservice.controllers.DO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ResetPasswordCreationRequest {
    private UUID requestId;
    private UUID clubId;
    private String resetCode;
    private Instant expirationDate;
}
