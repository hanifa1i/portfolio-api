package com.hanif.portfolioapi.dto.auth;

import com.hanif.portfolioapi.validation.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = ValidationMessages.REQUIRED)
    private String username;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String password;
}
