package com.example.backend.Repository;

import com.example.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmailOrUsername(String email,String username);
}
