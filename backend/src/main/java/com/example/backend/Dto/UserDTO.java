package com.example.backend.Dto;

import com.example.backend.Entity.Role;

import lombok.Builder;

import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Integer Id;
    private String username;
    private String email;
    private String fullname;
    private String role;
    
}
