package com.example.backend.Mapstruct;

import com.example.backend.Entity.*;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Dto.*;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class MapperUser {
    @Autowired
    protected RoleRepository roleRepository;
    public UserDTO userToUserDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .Id(user.getId())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole().getName())
                .build();
    }
    public User registerToUser(RegisterRequest user) {
        if (user == null) {
            return null;
        }
        Role role = Role.builder().id(2).name("User").build();
        return User.builder()
                .fullname(user.getFullname())
                .email(user.getEmail())
                .username(user.getUsername())
                .password_hash(user.getPassword())
                .role(role)
                .build();
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        Role roleuser = roleRepository.findByName(userDTO.getRole());
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .fullname(userDTO.getFullname())
                .role(roleuser)
                .build();
    }

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        List<UserDTO> listdto = new ArrayList<>();
        for (User user : users) {
            listdto.add(userToUserDTO(user));
        }
        return listdto;
    }

}
