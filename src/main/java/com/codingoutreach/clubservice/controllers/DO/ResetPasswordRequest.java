package com.codingoutreach.clubservice.controllers.DO;

import lombok.*;

import javax.enterprise.inject.Alternative;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPasswordRequest {
    private String resetCode;
    private String newPassword;
}
