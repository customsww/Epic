package com.example.backend.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.backend.Dto.UserDTO;
import com.example.backend.Entity.User;
import com.example.backend.IService.IUserService;
import com.example.backend.Mapstruct.MapperUser;
import com.example.backend.Exception.*;
import com.example.backend.Repository.UserRepository;

@Service
public class UserService implements IUserService {
    UserRepository userRepository;
    MapperUser mapperUser;
    private String apiKey;

    @Override
    public List<UserDTO> getAllUser(){
        try {
            List<User> users = Optional.of(userRepository.findAll())
                                        .filter(list -> !list.isEmpty())
                                        .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));
            return mapperUser.usersToUserDTOs(users);
        } catch (Exception e) {
            throw new GeneralException("Lỗi khi lấy danh sách người dùng: " + e.getMessage(), e);
        }
    }
}
