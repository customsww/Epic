package com.example.backend.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    public String username;
    public String password;
}
