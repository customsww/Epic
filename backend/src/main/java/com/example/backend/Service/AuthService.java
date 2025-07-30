package com.example.backend.Service;

import com.example.backend.Dto.Response;
import com.example.backend.Dto.UserDTO;
import com.example.backend.Entity.User;
import com.example.backend.Exception.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.backend.Config.JwtUtils;
import com.example.backend.Dto.LoginRequest;
import com.example.backend.Dto.RegisterRequest;
import com.example.backend.IService.IAuthService;
import com.example.backend.Mapstruct.MapperUser;
import com.example.backend.Repository.UserRepository;

import io.swagger.v3.oas.annotations.servers.Server;
    
@Service
public class AuthService implements IAuthService {
    @Autowired
    private JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MapperUser mapperUser;

    @Autowired
    public AuthService(UserRepository userRepository, MapperUser mapperUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.mapperUser = mapperUser;
    }

    @Override
    public Response Login(LoginRequest accountlogin) {
        try {
            User user = userRepository.findByEmailOrUsername(accountlogin.username, accountlogin.username)
                    .orElseThrow(() -> new NotFoundException(
                            "Đăng nhập không thành công. Vui lòng kiểm tra tên đăng nhập và mật khẩu"));
            boolean authenticated = passwordEncoder.matches(user.getPassword_hash(), accountlogin.password);
            if (!authenticated) {
                return Response.builder()
                        .status(false)
                        .message("Đăng nhập không thành công. Vui lòng kiểm tra tên đăng nhập và mật khẩu")
                        .build();
            }
            UserDTO userReponse = mapperUser.userToUserDTO(user);
            String token = jwtUtils.generateToken(userReponse);
            return Response.builder()
                    .status(true)
                    .message(token)
                    .build();
        } catch (Exception e) {
            throw new NotFoundException("Đăng nhập thất bại: " + e.getMessage());
        }
    }

    @Override
    public Response Register(RegisterRequest form_register){
        if (form_register == null) {
            return Response.builder()
            .status(false).message("Vui lòng nhập đủ thông tin").build();
        }
        User user = mapperUser.registerToUser(form_register);
        user.setPassword_hash(passwordEncoder.encode(form_register.getPassword()));
        try {
            user = userRepository.save(user);
        }
        catch (DataIntegrityViolationException exception) {
                if (exception.getCause() instanceof ConstraintViolationException) {
                    ConstraintViolationException constraintViolation = (ConstraintViolationException) exception.getCause();
                    String message = constraintViolation.getSQLException().getMessage();
                    if (message.contains("email")) {
                        throw new DuplicateResourceException ("Email đã tồn tại");
                    } else if (message.contains("username")) {
                        throw new DuplicateResourceException("Username đã tồn tại");
                    }
                }
                throw new DuplicateResourceException("Người dùng đẫ tồn tại");
            }
            
            return Response.builder().status(true).message("Đăng kí thành công").build();
    }
    }

