package com.polystore.polystorebackend.api.auth;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterResponse {
    private String username;
    private String role;
    private String token;
}
