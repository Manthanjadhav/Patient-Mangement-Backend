package com.manthan.auth_service.Repository;

import com.manthan.auth_service.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User,UUID> {
    Optional<User> findByEmail(String email);
}
