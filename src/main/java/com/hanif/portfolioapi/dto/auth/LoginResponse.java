package com.hanif.portfolioapi.dto.auth;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String username;
}
