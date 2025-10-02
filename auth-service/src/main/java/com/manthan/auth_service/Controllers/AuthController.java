package com.manthan.auth_service.Controllers;

import com.manthan.auth_service.dto.LoginRequestDTO;
import com.manthan.auth_service.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public class AuthController {
    @Operation(summary = "Generated token on login")
    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        if(tokenOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptional.get();

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
