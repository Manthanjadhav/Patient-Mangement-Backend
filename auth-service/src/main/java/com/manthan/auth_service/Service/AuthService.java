package com.manthan.auth_service.Service;

import com.manthan.auth_service.Repository.UserRepository;
import com.manthan.auth_service.dto.LoginRequestDTO;
import com.manthan.auth_service.models.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO){
        Optional<User> user = userService.findByEmail(loginRequestDTO.getEmail())
                .filter(u->passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
                .map(u->jwtUtil.generateToken(u.getEmail(), u.getRole()));
    }
}
