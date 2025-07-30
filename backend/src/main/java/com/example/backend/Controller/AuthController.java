package com.example.backend.Controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.backend.Config.JwtUtils;
import com.example.backend.Dto.*;
import com.example.backend.Service.AuthService;
import com.example.backend.Exception.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Response user = authService.Login(request);
        if (user.getStatus()) {
            jwtUtils.addTokenToCookie(response, user.message);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(user.getMessage());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpServletResponse response) {

        try {
            Response user = authService.Register(request);
            if (user.getStatus()) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.badRequest().body(user.getMessage());
        } catch (DuplicateResourceException ex) {
            Response errorResponse = Response.builder()
                    .status(false)
                    .message(ex.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

        }
    }
}
