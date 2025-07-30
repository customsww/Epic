package com.example.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.Entity.Role;
import java.util.List;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
