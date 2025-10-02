package com.manthan.auth_service.Service;

import com.manthan.auth_service.Repository.UserRepository;
import com.manthan.auth_service.models.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
