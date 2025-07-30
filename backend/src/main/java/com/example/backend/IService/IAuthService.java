package com.example.backend.IService;

import com.example.backend.Dto.Response;
import com.example.backend.Dto.LoginRequest;
import com.example.backend.Dto.RegisterRequest;

public interface IAuthService {
    Response Login(LoginRequest account);
    Response Register(RegisterRequest form_register);
}
