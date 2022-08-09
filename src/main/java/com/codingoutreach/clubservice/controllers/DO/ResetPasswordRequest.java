package com.codingoutreach.clubservice.controllers.DO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.enterprise.inject.Alternative;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ResetPasswordRequest {
    private UUID clubId;
    private String resetCode;
    private String newPassword;
}
