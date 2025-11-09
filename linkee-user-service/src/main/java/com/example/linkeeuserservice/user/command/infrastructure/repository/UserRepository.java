package com.example.linkeeuserservice.user.command.infrastructure.repository;


import com.example.linkeeuserservice.user.command.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);

}
